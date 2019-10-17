package py.com.global.spm.GUISERVICE.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.IRolDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBRol;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.dto.Rol.*;
import py.com.global.spm.GUISERVICE.specifications.RolSpecs;
import py.com.global.spm.GUISERVICE.utils.GeneralHelper;
import py.com.global.spm.GUISERVICE.utils.ListHelper;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.domain.entity.*;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by cbenitez on 3/21/18.
 */
@Transactional
@Service
public class RolService{

    public static final Logger logger = LogManager
            .getLogger(RolService.class);

    private static String USUARIO="usuario";
    private static String MESSAGE="message";
    private static final List<String> worflowTipoOP = Stream.of("CONSULTAR_WORKFLOW", "CONSULTAR_TIPO_ORDEN_DE_PAGO").collect(Collectors.toList());
    private static final List<String> permissionValidation = Stream.of("CONSULTAR_ORDEN_DE_PAGO", "CONSULTAR_BORRADOR", "MODIFICAR_BORRADOR").collect(Collectors.toList());


    @Autowired
    private IRolDao dao;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private SuperCompanyService superCompanyService;

    @Autowired
    private GeneralHelper generalHelper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolPermissionService rolPermissionService;

    /**
     * Crear o actualizar un Rol
     *
     * @param rol
     * @return {@link Rol}
     * @throws Exception
     */
    public Rol saveOrUpdate(Rol rol) throws Exception {
        try {
            return dao.save(rol);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Obtener un rol por su id.
     *
     * @param id
     * @return {@link Rol}
     */
    public Rol getRolById(Long id) {
        return dao.findByIdrolPk(id);
    }

    /**
     * Obtener un SystemParameter por su nombre de parametro.
     *
     * @return {@link Rol}
     */
    public List<Rol> getRolByDefaultrolNumAndStateNum(Boolean defaultrolNum, boolean stateNum) throws Exception {
        try {
            return dao.findByDefaultrolNumAndStateNum(defaultrolNum,stateNum);
        }catch (Exception e){
            throw e;
        }
    }


    public ResponseDto getRolByFilter(@NotNull(message = "0030") CBRol criteriosBusqueda, String direction, String properties, Integer pagina,
                                      Integer rows) {

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);

        try {

            if (superCompanyService.getLoggedUserCompany()==null) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOCOMPANY.getValue(), null);
            }

            Specification<Rol> where = null;
            //if(!superCompanyService.isLoggedUserFromSuperCompany()){
               // Long idCompany = superCompanyService.getLoggedUserCompany().getIdcompanyPk();
               // where = Specification.where(RolSpecs.getByCompanyId(idCompany));
           // }else{
                where = Specification.where(RolSpecs.getAll());

                //if (criteriosBusqueda.getCompanyId()!= null && !criteriosBusqueda.getCompanyId().isEmpty()) {
                  //  where = where.and(RolSpecs.getByCompanyId(Long.parseLong(criteriosBusqueda.getCompanyId())));
                //}
                if (criteriosBusqueda.getDefaultrolNum() != null) {
                    where = where.and(RolSpecs.getByDefaultrolNum(criteriosBusqueda.getDefaultrolNum()));
                }
            //}

            if (criteriosBusqueda.getRolnameChr() != null && !criteriosBusqueda.getRolnameChr().isEmpty()) {
                where = where.and(RolSpecs.getByNombre(criteriosBusqueda.getRolnameChr()));
            }
            if (criteriosBusqueda.getStateNum() != null) {
                where = where.and(RolSpecs.getByState(criteriosBusqueda.getStateNum()));
            }
            if(!superCompanyService.isSuperCompany()){
                where = where.and(RolSpecs.getByIsNotSuperRol());
            }

            Page<Rol> roles = dao.findAll(where, pageRequest);

            String code;
            if (roles.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("roles", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (roles.getContent().isEmpty()) {
                    code = DataDTO.CODE.OKNODATA.getValue();
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("roles");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }
                body.put("message", respuesta);
                body.put("roles", addToRolListResponseDto(roles.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto.setMeta(new MetaDTO(roles.getSize(), roles.getTotalPages(), roles.getTotalElements()));
            return responseDto;

        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de roles", e);
            return null;
        }
    }

    private Sort ordenamiento(String direction, String properties) {

        String dire = direction;
        String prope = properties;

        /*Ordenamiento por defecto por fechaDesde, de manera ascendente*/
        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "idrolPk";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);
    }

    public List<RolResponseListDto> addToRolListResponseDto(List<Rol> rolList) {

        List<RolResponseListDto> dtoList = new ArrayList<>();

        if (ListHelper.hasElements(rolList)) {
            for (Rol c : rolList) {
                RolResponseListDto dto = new RolResponseListDto();
                dto.setIdrolPk(c.getIdrolPk());
                dto.setRolnameChr(c.getRolnameChr());
                //dto.setCompanyNameChr(c.getCompany().getCompanynameChr());
                dto.setDescriptionChr(c.getDescriptionChr());
                dto.setDefaultrolNum(c.getDefaultrolNum());
                dto.setStateNum(c.isStateNum());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    public ResponseDto getRolByIdResponse(Long id) throws Exception{
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        try {
            Rol rol = getRolById(id);

            if(rol == null){
                replaceText.add("rol");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText);
            }

            replaceText.add("rol");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.GETMASC.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("rol", addToRolResponseDto(rol));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener el rol. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(),replaceText);
        }
    }

    public RolDto addToRolResponseDto(Rol r) {

        RolDto dto = new RolDto();
        dto.setIdrolPk(r.getIdrolPk());
        dto.setRolnameChr(r.getRolnameChr());
        dto.setDescriptionChr(r.getDescriptionChr());
        //dto.setCompany(r.getCompany());
        dto.setStateNum(r.isStateNum());
        dto.setDefaultrolNum(r.getDefaultrolNum());

        return dto;
    }


//    public ResponseDto getPermissionStructure() throws Exception{
//        ResponseDto responseDto = new ResponseDto();
//        Messages messages;
//        List<String> replaceText = new ArrayList<>();
//        DataDTO dataDTO = new DataDTO();
//        try {
//
//            List<MenuStructure> menuStructuresModuls = menuStructureService.getByParentMenuId(1L);
//            List<TreeNode> modulos = new ArrayList<TreeNode>();
//
//            for(MenuStructure m : menuStructuresModuls){
//                TreeNode modulo = new TreeNode();
//                modulo.setData(m.getIdmenuPk());
//                modulo.setLabel(m.getNameChr());
//                modulo.setType("modulo");
//                modulo.setLeaf(false);
//                modulo.setCollapsedIcon("fa-folder");
//                modulo.setExpandedIcon("fa-folder-open");
//
//                List<TreeNode> submodulos = new ArrayList<TreeNode>();
//                List<MenuStructure> menuStructuresSubModuls = menuStructureService.getByParentMenuId(m.getIdmenuPk());
//                for(MenuStructure ms : menuStructuresSubModuls) {
//                    TreeNode subModulo = new TreeNode();
//                    subModulo.setData(ms.getIdmenuPk());
//                    subModulo.setLabel(ms.getNameChr());
//                    subModulo.setType("modulo");
//                    subModulo.setLeaf(false);
//                    subModulo.setCollapsedIcon("fa-folder");
//                    subModulo.setExpandedIcon("fa-folder-open");
//                    submodulos.add(subModulo);
//
//                    List<MenuStructure> menuStructuresPermisos = menuStructureService.getByParentMenuId(ms.getIdmenuPk());
//                    List<TreeNode> permisos = new ArrayList<TreeNode>();
//                    for(MenuStructure p : menuStructuresPermisos){
//                        TreeNode permiso = new TreeNode();
//                        permiso.setData(p.getIdmenuPk());
//                        permiso.setLabel(p.getNameChr());
//                        permiso.setSelectable(true);
//                        permiso.setLeaf(true);
//                        permiso.setIcon("fa-file-word-o");
//                        permiso.setType("permiso");
//                        permisos.add(permiso);
//                    }
//                    subModulo.setChildren(permisos);
//                }
//                modulo.setChildren(submodulos);
//                modulos.add(modulo);
//            }
//
//            replaceText.add("permisos");
//            String mensaje = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
//            Map<String, Object> body = new HashMap<>();
//            body.put("mensaje", mensaje);
//            body.put("permisos", modulos);
//
//            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
//            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
//            dataDTO.setMessage(messages);
//            dataDTO.setBody(body);
//
//            responseDto.setData(dataDTO);
//
//            return responseDto;
//        } catch (Exception e) {
//            logger.error("Error al obtener los permisos. ", e);
//            return null;
//        }
//    }

    /**
     * Lista de todos los permisos disponibles
     * @return
     */
    public ResponseDto getAllPermissions(){
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        try {
//            List<MenuStructure> permisos = menuStructureService.getBySheetNum(true);
            List<Permission> permisos = permissionService.getAll();

            replaceText.add("permisos");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("permisos", permisos);

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener los permisos. ", e);
            return null;
        }
    }

    /**
     * Metodo que obtiene los permisos del usuario logueado con id
     * @return
     */
    public ResponseDto getPermissionsSelectedForRol(Long idRol){
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        List<Permission> permissionList = new ArrayList<>();
        try {
            Rol rol = dao.findByIdrolPk(idRol);
            if(rol != null){
                List<RolPermission> rolPermissionList = rolPermissionService.getByIdRol(rol.getIdrolPk());

                for (RolPermission rolPermission : rolPermissionList){
                    if(rolPermission.getActiveNum()){
                        permissionList.add(rolPermission.getPermission());
                    }
                }
            }else{
                replaceText.add("Rol");
                replaceText.add(idRol.toString());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(),replaceText);
            }

            replaceText.add("permisos");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("permisos", permissionList);

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener los permisos. ", e);
            return null;
        }
    }

    /**
     * Obtiene los permisos de tipo Authority del usuario logueado.
     * @return
     */
    public ResponseDto getAllPermisosByUserLoggedIn(){
        try {
            ResponseDto responseDto = new ResponseDto();
            DataDTO dataDTO = new DataDTO();
            Messages msg;
            Map<String,Object> body = new HashMap<>();

            Collection<? extends GrantedAuthority> listPermisos = SecurityContextHolder.getContext()
                    .getAuthentication().getAuthorities();
            List<String> listaPermisosString = new ArrayList<>();

            /*Preparar respuesta*/
            msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(),null);
            dataDTO.setMessage(msg);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            String mensaje;
            if(!ListHelper.hasElements(listPermisos)){
                List<String> replaceText = new ArrayList<>();
                replaceText.add("usuario");
                replaceText.add(Rol.class.getSimpleName());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOROLASOC.getValue(),replaceText);
            }

            for(GrantedAuthority permiso: listPermisos){
                listaPermisosString.add(permiso.getAuthority());
            }
            List<String> replaceText = new ArrayList<>();
            replaceText.add("permisos");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(),replaceText);

            body.put(MESSAGE, mensaje);
            body.put("permisos", listaPermisosString);
            dataDTO.setBody(body);
            /*Respuesta*/
            responseDto.setData(dataDTO);
            responseDto.setMeta(null);
            responseDto.setErrors(null);
            return responseDto;
        }catch (Exception e) {
            logger.warn("Error al obtener permisos", e);
            return null;
        }
    }


    public ResponseDto getRoles() throws Exception{
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        try {
            List<Rol> roles = dao.findAll();

            if(roles == null){
                replaceText.add("rol");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NONELISTFEM.getValue(), replaceText);
            }

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);

            replaceText.add("roles");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("roles", addToRolListResponseDto(roles));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener las roles. ", e);
            return null;
        }
    }

    /**
     * Este metodo se encarga de editar un rol.
     *
     * @param request
     * @return {@link ResponseDto}
     */
    public ResponseDto editRol(RolRequestDto request) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();

        try {

            if (!generalHelper.isValidUserCompany(request.getCompanyId()) || !superCompanyService.isLoggedUserFromSuperCompany()){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(),null);
            }

            Rol rol = dao.findByIdrolPk(request.getIdrolPk());

            if (rol == null) {
                replaceText.add("Rol");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }


            if(request.getListPermisos().isEmpty()){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.OBLIGATORIESFIELDS.getValue(), null);
            }
            //Validamos los permisos requeridos
            Map<String, Long> permissions = request.getListPermisos();
            for(String permission: permissionValidation) {
                if(permissions.get(permission)!=null){
                    for (String reqPermission: worflowTipoOP){
                        if(permissions.get(reqPermission)==null)
                            return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDPERM.getValue(),Stream.of(permission,reqPermission).collect(Collectors.toList()));
                    }
                }
            }


            rol.setStateNum(request.getStateNum());
            rol.setDefaultrolNum(request.getDefaultrolNum());

            //Solo se edita la lista de permisos para una super empresa
            List<RolPermission> rolPermissions = rolPermissionService.getByIdRol(request.getIdrolPk());
            for(int i=0;i<rolPermissions.size();i++){
                rolPermissions.get(i).setActiveNum(false);
            }
            rolPermissionService.saveOrUpdateList(rolPermissions);

            for(Long idpermission : request.getListPermisos().values()){
                RolPermission rp = rolPermissionService.getByIdRolAndIdPermission(request.getIdrolPk(),idpermission);
                if(rp==null){
                    RolPermission rpNew = new RolPermission();
                    RolPermissionId rpId = new RolPermissionId();
                    rpId.setIdrolPk(request.getIdrolPk());
                    rpId.setIdpermissionPk(idpermission);

                    rpNew.setRol(getRolById(request.getIdrolPk()));
                    rpNew.setPermission(permissionService.getById(idpermission));
                    rpNew.setActiveNum(true);
                    rpNew.setId(rpId);
                    rolPermissionService.saveOrUpdate(rpNew);
                }else{
                    rp.setActiveNum(true);
                    rolPermissionService.saveOrUpdate(rp);
                }
            }

            rol.setRolnameChr(request.getRolnameChr());
            rol.setDescriptionChr(request.getDescriptionChr());

            rol = saveOrUpdate(rol);
            replaceText.add("Rol");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.EDITEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("rol", addToRolResponseDto(rol));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Rol editado con éxito. ID: " + rol.getIdrolPk() + " Nombre: " + rol.getRolnameChr());

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar editar el rol: ", e);
            throw new Exception();
        }
    }

    public ResponseDto addRol(RolAddDto request) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();
        try {

            if(request.getListPermisos().isEmpty()){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.OBLIGATORIESFIELDS.getValue(), null);
            }
            //Validamos los permisos requeridos
            Map<String, Long> permissions = request.getListPermisos();
            for(String permission: permissionValidation) {
                if(permissions.get(permission)!=null){
                    for (String reqPermission: worflowTipoOP){
                        if(permissions.get(reqPermission)==null)
                            return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDPERM.getValue(),Stream.of(permission,reqPermission).collect(Collectors.toList()));
                    }
                }
            }
            Rol rol = new Rol();
            if(superCompanyService.isLoggedUserFromSuperCompany())
                rol.setDefaultrolNum(request.getDefaultrolNum());
            else
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(),null);

            rol.setStateNum(true);
            rol.setRolnameChr(request.getRolnameChr());
            rol.setDescriptionChr(request.getDescriptionChr());
            // Guardamos el rol
            rol = saveOrUpdate(rol);
            for(Long idPermission : request.getListPermisos().values()){
                Permission permission = permissionService.getById(idPermission);
                if(permission != null){
                    RolPermission rpNew = new RolPermission();
                    RolPermissionId rpId = new RolPermissionId();
                    rpId.setIdrolPk(rol.getIdrolPk());
                    rpId.setIdpermissionPk(idPermission);

                    rpNew.setPermission(permission);
                    rpNew.setRol(rol);
                    rpNew.setActiveNum(true);
                    rpNew.setId(rpId);
                    rolPermissionService.saveOrUpdate(rpNew);
                }
            }

//            Rol r = saveOrUpdate(rol);

            replaceText.add("Rol");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.CREATEEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("rol",addToRolResponseDto(rol));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Rol creado con éxito. ID: " + rol.getIdrolPk() + " Nombre: " + rol.getRolnameChr());

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar crear el Rol: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(),replaceText);
        }
    }

//    public ResponseDto getPermissionsForUser() throws Exception{
//        ResponseDto responseDto = new ResponseDto();
//        Messages messages;
//        List<String> replaceText = new ArrayList<>();
//        DataDTO dataDTO = new DataDTO();
//        User user = userService.getLoggedUser();
//        Set<Rol> rolList = user.getRols();
//        List<TreeNode> treeNodeList = new ArrayList<>();
//        List<RolMenu> rolMenuList = new ArrayList<>();
//
//        try {
//            if(ListHelper.hasElements(rolList)){
//                Rol rol = rolList.stream().findFirst().get();
//                rolMenuList = rolMenuService.getByRolId(rol.getIdrolPk());
//
//                for(RolMenu rm : rolMenuList){
//                    TreeNode treeTmp = new TreeNode();
//                    treeTmp.setData(rm.getMenuStructure().getIdmenuPk());
//                    treeTmp.setLabel(rm.getMenuStructure().getNameChr());
//                    if(rm.getMenuStructure().getSheetNum()){
//                        treeTmp.setLeaf(true);
//                        treeTmp.setIcon("fa-file-word-o");
//                        treeTmp.setType("permiso");
//                    }else {
//                        treeTmp.setType("modulo");
//                        treeTmp.setLeaf(false);
//                        treeTmp.setCollapsedIcon("fa-folder");
//                        treeTmp.setExpandedIcon("fa-folder-open");
//                    }
//                    treeNodeList.add(treeTmp);
//                }
//            }
//
//            replaceText.add("permisos");
//            String mensaje = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
//            Map<String, Object> body = new HashMap<>();
//            body.put("mensaje", mensaje);
//            body.put("permisos", treeNodeList);
//
//            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
//            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
//            dataDTO.setMessage(messages);
//            dataDTO.setBody(body);
//
//            responseDto.setData(dataDTO);
//
//            return responseDto;
//        } catch (Exception e) {
//            logger.error("Error al obtener los permisos. ", e);
//            return null;
//        }
//    }

}

