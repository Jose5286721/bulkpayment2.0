
/**
 * TransaccionV2Pack.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:19:26 CET)
 */
            
                package us.inswitch.mts.ws.server;
            

            /**
            *  TransaccionV2Pack bean class
            */
        
        public  class TransaccionV2Pack
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = transaccionV2Pack
                Namespace URI = http://server.ws.mts.inswitch.us/
                Namespace Prefix = ns1
                */
            

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://server.ws.mts.inswitch.us/")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        

                        /**
                        * field for Cargo
                        */

                        
                                    protected java.math.BigDecimal localCargo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCargoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getCargo(){
                               return localCargo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Cargo
                               */
                               public void setCargo(java.math.BigDecimal param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCargoTracker = true;
                                       } else {
                                          localCargoTracker = false;
                                              
                                       }
                                   
                                            this.localCargo=param;
                                    

                               }
                            

                        /**
                        * field for Comision
                        */

                        
                                    protected java.math.BigDecimal localComision ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localComisionTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getComision(){
                               return localComision;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Comision
                               */
                               public void setComision(java.math.BigDecimal param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localComisionTracker = true;
                                       } else {
                                          localComisionTracker = false;
                                              
                                       }
                                   
                                            this.localComision=param;
                                    

                               }
                            

                        /**
                        * field for Credito
                        */

                        
                                    protected java.math.BigDecimal localCredito ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreditoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getCredito(){
                               return localCredito;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Credito
                               */
                               public void setCredito(java.math.BigDecimal param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCreditoTracker = true;
                                       } else {
                                          localCreditoTracker = false;
                                              
                                       }
                                   
                                            this.localCredito=param;
                                    

                               }
                            

                        /**
                        * field for Debito
                        */

                        
                                    protected java.math.BigDecimal localDebito ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDebitoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getDebito(){
                               return localDebito;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Debito
                               */
                               public void setDebito(java.math.BigDecimal param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localDebitoTracker = true;
                                       } else {
                                          localDebitoTracker = false;
                                              
                                       }
                                   
                                            this.localDebito=param;
                                    

                               }
                            

                        /**
                        * field for Fecha
                        */

                        
                                    protected java.lang.String localFecha ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFechaTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFecha(){
                               return localFecha;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fecha
                               */
                               public void setFecha(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localFechaTracker = true;
                                       } else {
                                          localFechaTracker = false;
                                              
                                       }
                                   
                                            this.localFecha=param;
                                    

                               }
                            

                        /**
                        * field for IdCuenta
                        */

                        
                                    protected java.lang.String localIdCuenta ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdCuentaTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIdCuenta(){
                               return localIdCuenta;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IdCuenta
                               */
                               public void setIdCuenta(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localIdCuentaTracker = true;
                                       } else {
                                          localIdCuentaTracker = false;
                                              
                                       }
                                   
                                            this.localIdCuenta=param;
                                    

                               }
                            

                        /**
                        * field for IdTrans
                        */

                        
                                    protected int localIdTrans ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdTransTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getIdTrans(){
                               return localIdTrans;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IdTrans
                               */
                               public void setIdTrans(int param){
                            
                                       // setting primitive attribute tracker to true
                                       
                                               if (param==java.lang.Integer.MIN_VALUE) {
                                           localIdTransTracker = false;
                                              
                                       } else {
                                          localIdTransTracker = true;
                                       }
                                   
                                            this.localIdTrans=param;
                                    

                               }
                            

                        /**
                        * field for MovilDestino
                        */

                        
                                    protected java.lang.String localMovilDestino ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMovilDestinoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMovilDestino(){
                               return localMovilDestino;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MovilDestino
                               */
                               public void setMovilDestino(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMovilDestinoTracker = true;
                                       } else {
                                          localMovilDestinoTracker = false;
                                              
                                       }
                                   
                                            this.localMovilDestino=param;
                                    

                               }
                            

                        /**
                        * field for MovilOrigen
                        */

                        
                                    protected java.lang.String localMovilOrigen ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMovilOrigenTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMovilOrigen(){
                               return localMovilOrigen;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MovilOrigen
                               */
                               public void setMovilOrigen(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMovilOrigenTracker = true;
                                       } else {
                                          localMovilOrigenTracker = false;
                                              
                                       }
                                   
                                            this.localMovilOrigen=param;
                                    

                               }
                            

                        /**
                        * field for Resultado
                        */

                        
                                    protected java.lang.String localResultado ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResultadoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResultado(){
                               return localResultado;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Resultado
                               */
                               public void setResultado(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localResultadoTracker = true;
                                       } else {
                                          localResultadoTracker = false;
                                              
                                       }
                                   
                                            this.localResultado=param;
                                    

                               }
                            

                        /**
                        * field for Servicio
                        */

                        
                                    protected java.lang.String localServicio ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localServicioTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getServicio(){
                               return localServicio;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Servicio
                               */
                               public void setServicio(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localServicioTracker = true;
                                       } else {
                                          localServicioTracker = false;
                                              
                                       }
                                   
                                            this.localServicio=param;
                                    

                               }
                            

                        /**
                        * field for Tipo
                        */

                        
                                    protected java.lang.String localTipo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTipoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTipo(){
                               return localTipo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Tipo
                               */
                               public void setTipo(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localTipoTracker = true;
                                       } else {
                                          localTipoTracker = false;
                                              
                                       }
                                   
                                            this.localTipo=param;
                                    

                               }
                            

     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;
        
        try{
          isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(java.lang.IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }
     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       TransaccionV2Pack.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);
            
       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://server.ws.mts.inswitch.us/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":transaccionV2Pack",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "transaccionV2Pack",
                           xmlWriter);
                   }

               
                   }
                if (localCargoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"cargo", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"cargo");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("cargo");
                                    }
                                

                                          if (localCargo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("cargo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCargo));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localComisionTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"comision", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"comision");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("comision");
                                    }
                                

                                          if (localComision==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("comision cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localComision));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCreditoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"credito", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"credito");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("credito");
                                    }
                                

                                          if (localCredito==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("credito cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCredito));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDebitoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"debito", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"debito");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("debito");
                                    }
                                

                                          if (localDebito==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("debito cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDebito));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFechaTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"fecha", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"fecha");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("fecha");
                                    }
                                

                                          if (localFecha==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fecha cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFecha);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIdCuentaTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"idCuenta", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"idCuenta");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("idCuenta");
                                    }
                                

                                          if (localIdCuenta==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("idCuenta cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIdCuenta);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIdTransTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"idTrans", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"idTrans");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("idTrans");
                                    }
                                
                                               if (localIdTrans==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("idTrans cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdTrans));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMovilDestinoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"movilDestino", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"movilDestino");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("movilDestino");
                                    }
                                

                                          if (localMovilDestino==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("movilDestino cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMovilDestino);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMovilOrigenTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"movilOrigen", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"movilOrigen");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("movilOrigen");
                                    }
                                

                                          if (localMovilOrigen==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("movilOrigen cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMovilOrigen);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResultadoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"resultado", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"resultado");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("resultado");
                                    }
                                

                                          if (localResultado==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("resultado cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResultado);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localServicioTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"servicio", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"servicio");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("servicio");
                                    }
                                

                                          if (localServicio==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("servicio cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localServicio);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTipoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"tipo", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"tipo");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("tipo");
                                    }
                                

                                          if (localTipo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("tipo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTipo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
                java.lang.String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localCargoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "cargo"));
                                 
                                        if (localCargo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCargo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("cargo cannot be null!!");
                                        }
                                    } if (localComisionTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "comision"));
                                 
                                        if (localComision != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localComision));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("comision cannot be null!!");
                                        }
                                    } if (localCreditoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "credito"));
                                 
                                        if (localCredito != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCredito));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("credito cannot be null!!");
                                        }
                                    } if (localDebitoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "debito"));
                                 
                                        if (localDebito != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDebito));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("debito cannot be null!!");
                                        }
                                    } if (localFechaTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fecha"));
                                 
                                        if (localFecha != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFecha));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fecha cannot be null!!");
                                        }
                                    } if (localIdCuentaTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "idCuenta"));
                                 
                                        if (localIdCuenta != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdCuenta));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("idCuenta cannot be null!!");
                                        }
                                    } if (localIdTransTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "idTrans"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdTrans));
                            } if (localMovilDestinoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "movilDestino"));
                                 
                                        if (localMovilDestino != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMovilDestino));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("movilDestino cannot be null!!");
                                        }
                                    } if (localMovilOrigenTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "movilOrigen"));
                                 
                                        if (localMovilOrigen != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMovilOrigen));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("movilOrigen cannot be null!!");
                                        }
                                    } if (localResultadoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "resultado"));
                                 
                                        if (localResultado != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResultado));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("resultado cannot be null!!");
                                        }
                                    } if (localServicioTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "servicio"));
                                 
                                        if (localServicio != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServicio));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("servicio cannot be null!!");
                                        }
                                    } if (localTipoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "tipo"));
                                 
                                        if (localTipo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTipo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("tipo cannot be null!!");
                                        }
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static TransaccionV2Pack parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            TransaccionV2Pack object =
                new TransaccionV2Pack();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"transaccionV2Pack".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (TransaccionV2Pack)us.inswitch.mts.ws.server.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","cargo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCargo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","comision").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setComision(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","credito").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCredito(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","debito").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDebito(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fecha").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFecha(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","idCuenta").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIdCuenta(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","idTrans").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIdTrans(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setIdTrans(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","movilDestino").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMovilDestino(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","movilOrigen").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMovilOrigen(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","resultado").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResultado(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","servicio").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setServicio(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","tipo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTipo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
          