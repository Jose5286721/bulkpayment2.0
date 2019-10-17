package py.com.global.spm.GUISERVICE.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import py.com.global.spm.domain.entity.RolPermission;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cdelgado
 * @version 1.0
 * @since 05/12/17
 */
public final class UserFactory {

    private UserFactory() {
    }

    public static User create(py.com.global.spm.domain.entity.User user) {
        return new User(
                user.getIduserPk(),
                user.getEmailChr(),
                user.getUserlastnameChr(),
                user.getPasswordChr(),
                mapToGrantedAuthorities(user),
                SPM_GUI_Constants.ACTIVO.equals(user.getStateChr()),
                user.getPasschangedateTim(),
                user.getTokenChr(),
                user.getTokenvalidateTim());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(py.com.global.spm.domain.entity.User user) {
        List<String> permisos = new ArrayList<>();

        if(user.isNewUser())
            permisos = Stream.of("ROLE_CONSULTAR_USUARIO","ROLE_MODIFICAR_USUARIO").collect(Collectors.toList());
        else {
            if(user.getRols().isStateNum()) {
                List<RolPermission> rolPermissionList = new ArrayList<>(user.getRols().getRolPermissions());
                for (RolPermission rolPermission : rolPermissionList) {
                    if (Boolean.TRUE.equals(rolPermission.getActiveNum())) {
                        permisos.add("ROLE_" + rolPermission.getPermission().getNameChr());
                    }
                }
            }
        }
        String cadenaPermisos = StringUtils.join(permisos, ',');
        return AuthorityUtils.commaSeparatedStringToAuthorityList(cadenaPermisos);
    }
}
