
/**
 * TransaccionPack.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:19:26 CET)
 */
            
                package us.inswitch.mts.ws.server;
            

            /**
            *  TransaccionPack bean class
            */
        
        public  class TransaccionPack extends us.inswitch.mts.ws.server.BasicResponsePack
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = transaccionPack
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
                        * field for CodPagos
                        */

                        
                                    protected java.lang.String localCodPagos ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCodPagosTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCodPagos(){
                               return localCodPagos;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CodPagos
                               */
                               public void setCodPagos(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCodPagosTracker = true;
                                       } else {
                                          localCodPagosTracker = false;
                                              
                                       }
                                   
                                            this.localCodPagos=param;
                                    

                               }
                            

                        /**
                        * field for DescPagos
                        */

                        
                                    protected java.lang.String localDescPagos ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDescPagosTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDescPagos(){
                               return localDescPagos;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DescPagos
                               */
                               public void setDescPagos(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localDescPagosTracker = true;
                                       } else {
                                          localDescPagosTracker = false;
                                              
                                       }
                                   
                                            this.localDescPagos=param;
                                    

                               }
                            

                        /**
                        * field for DetalleTrans
                        * This was an Array!
                        */

                        
                                    protected us.inswitch.mts.ws.server.MovimientoPack[] localDetalleTrans ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDetalleTransTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return us.inswitch.mts.ws.server.MovimientoPack[]
                           */
                           public  us.inswitch.mts.ws.server.MovimientoPack[] getDetalleTrans(){
                               return localDetalleTrans;
                           }

                           
                        


                               
                              /**
                               * validate the array for DetalleTrans
                               */
                              protected void validateDetalleTrans(us.inswitch.mts.ws.server.MovimientoPack[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param DetalleTrans
                              */
                              public void setDetalleTrans(us.inswitch.mts.ws.server.MovimientoPack[] param){
                              
                                   validateDetalleTrans(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localDetalleTransTracker = true;
                                          } else {
                                             localDetalleTransTracker = true;
                                                 
                                          }
                                      
                                      this.localDetalleTrans=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param us.inswitch.mts.ws.server.MovimientoPack
                             */
                             public void addDetalleTrans(us.inswitch.mts.ws.server.MovimientoPack param){
                                   if (localDetalleTrans == null){
                                   localDetalleTrans = new us.inswitch.mts.ws.server.MovimientoPack[]{};
                                   }

                            
                                 //update the setting tracker
                                localDetalleTransTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localDetalleTrans);
                               list.add(param);
                               this.localDetalleTrans =
                             (us.inswitch.mts.ws.server.MovimientoPack[])list.toArray(
                            new us.inswitch.mts.ws.server.MovimientoPack[list.size()]);

                             }
                             

                        /**
                        * field for Error
                        */

                        
                                    protected java.lang.String localError ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localErrorTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getError(){
                               return localError;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Error
                               */
                               public void setError(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localErrorTracker = true;
                                       } else {
                                          localErrorTracker = false;
                                              
                                       }
                                   
                                            this.localError=param;
                                    

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
                        * field for IdTransaccion
                        */

                        
                                    protected int localIdTransaccion ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdTransaccionTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getIdTransaccion(){
                               return localIdTransaccion;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IdTransaccion
                               */
                               public void setIdTransaccion(int param){
                            
                                       // setting primitive attribute tracker to true
                                       
                                               if (param==java.lang.Integer.MIN_VALUE) {
                                           localIdTransaccionTracker = false;
                                              
                                       } else {
                                          localIdTransaccionTracker = true;
                                       }
                                   
                                            this.localIdTransaccion=param;
                                    

                               }
                            

                        /**
                        * field for Interfaz
                        */

                        
                                    protected java.lang.String localInterfaz ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInterfazTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInterfaz(){
                               return localInterfaz;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Interfaz
                               */
                               public void setInterfaz(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localInterfazTracker = true;
                                       } else {
                                          localInterfazTracker = false;
                                              
                                       }
                                   
                                            this.localInterfaz=param;
                                    

                               }
                            

                        /**
                        * field for Moneda
                        */

                        
                                    protected java.lang.String localMoneda ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMonedaTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMoneda(){
                               return localMoneda;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Moneda
                               */
                               public void setMoneda(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMonedaTracker = true;
                                       } else {
                                          localMonedaTracker = false;
                                              
                                       }
                                   
                                            this.localMoneda=param;
                                    

                               }
                            

                        /**
                        * field for Monto
                        */

                        
                                    protected java.math.BigDecimal localMonto ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMontoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getMonto(){
                               return localMonto;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Monto
                               */
                               public void setMonto(java.math.BigDecimal param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMontoTracker = true;
                                       } else {
                                          localMontoTracker = false;
                                              
                                       }
                                   
                                            this.localMonto=param;
                                    

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

                        
                                    protected int localResultado ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResultadoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getResultado(){
                               return localResultado;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Resultado
                               */
                               public void setResultado(int param){
                            
                                       // setting primitive attribute tracker to true
                                       
                                               if (param==java.lang.Integer.MIN_VALUE) {
                                           localResultadoTracker = false;
                                              
                                       } else {
                                          localResultadoTracker = true;
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
                       TransaccionPack.this.serialize(parentQName,factory,xmlWriter);
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
                

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://server.ws.mts.inswitch.us/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":transaccionPack",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "transaccionPack",
                           xmlWriter);
                   }

                if (localMensajeTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"mensaje", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"mensaje");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("mensaje");
                                    }
                                

                                          if (localMensaje==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("mensaje cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMensaje);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNroTransTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"nroTrans", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"nroTrans");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("nroTrans");
                                    }
                                
                                               if (localNroTrans==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("nroTrans cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNroTrans));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStatusTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"status", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"status");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("status");
                                    }
                                
                                               if (localStatus==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCodPagosTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"codPagos", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"codPagos");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("codPagos");
                                    }
                                

                                          if (localCodPagos==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("codPagos cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCodPagos);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDescPagosTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"descPagos", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"descPagos");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("descPagos");
                                    }
                                

                                          if (localDescPagos==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("descPagos cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDescPagos);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDetalleTransTracker){
                                       if (localDetalleTrans!=null){
                                            for (int i = 0;i < localDetalleTrans.length;i++){
                                                if (localDetalleTrans[i] != null){
                                                 localDetalleTrans[i].serialize(new javax.xml.namespace.QName("","detalleTrans"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                            // write null attribute
                                                            java.lang.String namespace2 = "";
                                                            if (! namespace2.equals("")) {
                                                                java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                                                if (prefix2 == null) {
                                                                    prefix2 = generatePrefix(namespace2);

                                                                    xmlWriter.writeStartElement(prefix2,"detalleTrans", namespace2);
                                                                    xmlWriter.writeNamespace(prefix2, namespace2);
                                                                    xmlWriter.setPrefix(prefix2, namespace2);

                                                                } else {
                                                                    xmlWriter.writeStartElement(namespace2,"detalleTrans");
                                                                }

                                                            } else {
                                                                xmlWriter.writeStartElement("detalleTrans");
                                                            }

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                // write null attribute
                                                java.lang.String namespace2 = "";
                                                if (! namespace2.equals("")) {
                                                    java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                                    if (prefix2 == null) {
                                                        prefix2 = generatePrefix(namespace2);

                                                        xmlWriter.writeStartElement(prefix2,"detalleTrans", namespace2);
                                                        xmlWriter.writeNamespace(prefix2, namespace2);
                                                        xmlWriter.setPrefix(prefix2, namespace2);

                                                    } else {
                                                        xmlWriter.writeStartElement(namespace2,"detalleTrans");
                                                    }

                                                } else {
                                                    xmlWriter.writeStartElement("detalleTrans");
                                                }

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localErrorTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"error", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"error");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("error");
                                    }
                                

                                          if (localError==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("error cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localError);
                                            
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
                             } if (localIdTransaccionTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"idTransaccion", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"idTransaccion");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("idTransaccion");
                                    }
                                
                                               if (localIdTransaccion==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("idTransaccion cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdTransaccion));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInterfazTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"interfaz", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"interfaz");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("interfaz");
                                    }
                                

                                          if (localInterfaz==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("interfaz cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInterfaz);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMonedaTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"moneda", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"moneda");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("moneda");
                                    }
                                

                                          if (localMoneda==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("moneda cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMoneda);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMontoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"monto", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"monto");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("monto");
                                    }
                                

                                          if (localMonto==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("monto cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMonto));
                                            
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
                                
                                               if (localResultado==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("resultado cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResultado));
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

                
                    attribList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema-instance","type"));
                    attribList.add(new javax.xml.namespace.QName("http://server.ws.mts.inswitch.us/","transaccionPack"));
                 if (localMensajeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "mensaje"));
                                 
                                        if (localMensaje != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMensaje));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("mensaje cannot be null!!");
                                        }
                                    } if (localNroTransTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "nroTrans"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNroTrans));
                            } if (localStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "status"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
                            } if (localCodPagosTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "codPagos"));
                                 
                                        if (localCodPagos != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCodPagos));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("codPagos cannot be null!!");
                                        }
                                    } if (localDescPagosTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "descPagos"));
                                 
                                        if (localDescPagos != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDescPagos));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("descPagos cannot be null!!");
                                        }
                                    } if (localDetalleTransTracker){
                             if (localDetalleTrans!=null) {
                                 for (int i = 0;i < localDetalleTrans.length;i++){

                                    if (localDetalleTrans[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("",
                                                                          "detalleTrans"));
                                         elementList.add(localDetalleTrans[i]);
                                    } else {
                                        
                                                elementList.add(new javax.xml.namespace.QName("",
                                                                          "detalleTrans"));
                                                elementList.add(null);
                                            
                                    }

                                 }
                             } else {
                                 
                                        elementList.add(new javax.xml.namespace.QName("",
                                                                          "detalleTrans"));
                                        elementList.add(localDetalleTrans);
                                    
                             }

                        } if (localErrorTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "error"));
                                 
                                        if (localError != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localError));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("error cannot be null!!");
                                        }
                                    } if (localFechaTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fecha"));
                                 
                                        if (localFecha != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFecha));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fecha cannot be null!!");
                                        }
                                    } if (localIdTransaccionTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "idTransaccion"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdTransaccion));
                            } if (localInterfazTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "interfaz"));
                                 
                                        if (localInterfaz != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterfaz));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("interfaz cannot be null!!");
                                        }
                                    } if (localMonedaTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "moneda"));
                                 
                                        if (localMoneda != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMoneda));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("moneda cannot be null!!");
                                        }
                                    } if (localMontoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "monto"));
                                 
                                        if (localMonto != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMonto));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("monto cannot be null!!");
                                        }
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
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResultado));
                            } if (localServicioTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "servicio"));
                                 
                                        if (localServicio != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServicio));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("servicio cannot be null!!");
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
        public static TransaccionPack parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            TransaccionPack object =
                new TransaccionPack();

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
                    
                            if (!"transaccionPack".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (TransaccionPack)us.inswitch.mts.ws.server.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 
                    
                    reader.next();
                
                        java.util.ArrayList list6 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","mensaje").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMensaje(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","nroTrans").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNroTrans(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNroTrans(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","status").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setStatus(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","codPagos").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCodPagos(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","descPagos").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDescPagos(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","detalleTrans").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list6.add(null);
                                                              reader.next();
                                                          } else {
                                                        list6.add(us.inswitch.mts.ws.server.MovimientoPack.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone6 = false;
                                                        while(!loopDone6){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone6 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("","detalleTrans").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list6.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list6.add(us.inswitch.mts.ws.server.MovimientoPack.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone6 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setDetalleTrans((us.inswitch.mts.ws.server.MovimientoPack[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                us.inswitch.mts.ws.server.MovimientoPack.class,
                                                                list6));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","error").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setError(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","idTransaccion").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIdTransaccion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setIdTransaccion(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","interfaz").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInterfaz(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","moneda").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMoneda(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","monto").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMonto(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
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
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setResultado(java.lang.Integer.MIN_VALUE);
                                           
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
           
          