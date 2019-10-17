
/**
 * MovBilletera.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:19:26 CET)
 */
            
                package us.inswitch.mts.ws.server;
            

            /**
            *  MovBilletera bean class
            */
        
        public  class MovBilletera
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = movBilletera
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
                        * field for Sesion
                        */

                        
                                    protected java.lang.String localSesion ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSesionTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSesion(){
                               return localSesion;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Sesion
                               */
                               public void setSesion(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localSesionTracker = true;
                                       } else {
                                          localSesionTracker = false;
                                              
                                       }
                                   
                                            this.localSesion=param;
                                    

                               }
                            

                        /**
                        * field for Fecha_ini_anho
                        */

                        
                                    protected java.lang.String localFecha_ini_anho ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFecha_ini_anhoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFecha_ini_anho(){
                               return localFecha_ini_anho;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fecha_ini_anho
                               */
                               public void setFecha_ini_anho(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localFecha_ini_anhoTracker = true;
                                       } else {
                                          localFecha_ini_anhoTracker = false;
                                              
                                       }
                                   
                                            this.localFecha_ini_anho=param;
                                    

                               }
                            

                        /**
                        * field for Fecha_ini_mes
                        */

                        
                                    protected java.lang.String localFecha_ini_mes ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFecha_ini_mesTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFecha_ini_mes(){
                               return localFecha_ini_mes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fecha_ini_mes
                               */
                               public void setFecha_ini_mes(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localFecha_ini_mesTracker = true;
                                       } else {
                                          localFecha_ini_mesTracker = false;
                                              
                                       }
                                   
                                            this.localFecha_ini_mes=param;
                                    

                               }
                            

                        /**
                        * field for Fecha_ini_dia
                        */

                        
                                    protected java.lang.String localFecha_ini_dia ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFecha_ini_diaTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFecha_ini_dia(){
                               return localFecha_ini_dia;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fecha_ini_dia
                               */
                               public void setFecha_ini_dia(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localFecha_ini_diaTracker = true;
                                       } else {
                                          localFecha_ini_diaTracker = false;
                                              
                                       }
                                   
                                            this.localFecha_ini_dia=param;
                                    

                               }
                            

                        /**
                        * field for Fecha_fin_anho
                        */

                        
                                    protected java.lang.String localFecha_fin_anho ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFecha_fin_anhoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFecha_fin_anho(){
                               return localFecha_fin_anho;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fecha_fin_anho
                               */
                               public void setFecha_fin_anho(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localFecha_fin_anhoTracker = true;
                                       } else {
                                          localFecha_fin_anhoTracker = false;
                                              
                                       }
                                   
                                            this.localFecha_fin_anho=param;
                                    

                               }
                            

                        /**
                        * field for Fecha_fin_mes
                        */

                        
                                    protected java.lang.String localFecha_fin_mes ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFecha_fin_mesTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFecha_fin_mes(){
                               return localFecha_fin_mes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fecha_fin_mes
                               */
                               public void setFecha_fin_mes(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localFecha_fin_mesTracker = true;
                                       } else {
                                          localFecha_fin_mesTracker = false;
                                              
                                       }
                                   
                                            this.localFecha_fin_mes=param;
                                    

                               }
                            

                        /**
                        * field for Fecha_fin_dia
                        */

                        
                                    protected java.lang.String localFecha_fin_dia ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFecha_fin_diaTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFecha_fin_dia(){
                               return localFecha_fin_dia;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fecha_fin_dia
                               */
                               public void setFecha_fin_dia(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localFecha_fin_diaTracker = true;
                                       } else {
                                          localFecha_fin_diaTracker = false;
                                              
                                       }
                                   
                                            this.localFecha_fin_dia=param;
                                    

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
                       MovBilletera.this.serialize(parentQName,factory,xmlWriter);
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
                           namespacePrefix+":movBilletera",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "movBilletera",
                           xmlWriter);
                   }

               
                   }
                if (localSesionTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"sesion", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"sesion");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("sesion");
                                    }
                                

                                          if (localSesion==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("sesion cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSesion);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFecha_ini_anhoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"fecha_ini_anho", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"fecha_ini_anho");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("fecha_ini_anho");
                                    }
                                

                                          if (localFecha_ini_anho==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fecha_ini_anho cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFecha_ini_anho);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFecha_ini_mesTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"fecha_ini_mes", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"fecha_ini_mes");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("fecha_ini_mes");
                                    }
                                

                                          if (localFecha_ini_mes==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fecha_ini_mes cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFecha_ini_mes);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFecha_ini_diaTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"fecha_ini_dia", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"fecha_ini_dia");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("fecha_ini_dia");
                                    }
                                

                                          if (localFecha_ini_dia==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fecha_ini_dia cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFecha_ini_dia);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFecha_fin_anhoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"fecha_fin_anho", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"fecha_fin_anho");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("fecha_fin_anho");
                                    }
                                

                                          if (localFecha_fin_anho==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fecha_fin_anho cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFecha_fin_anho);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFecha_fin_mesTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"fecha_fin_mes", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"fecha_fin_mes");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("fecha_fin_mes");
                                    }
                                

                                          if (localFecha_fin_mes==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fecha_fin_mes cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFecha_fin_mes);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFecha_fin_diaTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"fecha_fin_dia", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"fecha_fin_dia");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("fecha_fin_dia");
                                    }
                                

                                          if (localFecha_fin_dia==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fecha_fin_dia cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFecha_fin_dia);
                                            
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

                 if (localSesionTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "sesion"));
                                 
                                        if (localSesion != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSesion));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("sesion cannot be null!!");
                                        }
                                    } if (localFecha_ini_anhoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fecha_ini_anho"));
                                 
                                        if (localFecha_ini_anho != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFecha_ini_anho));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fecha_ini_anho cannot be null!!");
                                        }
                                    } if (localFecha_ini_mesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fecha_ini_mes"));
                                 
                                        if (localFecha_ini_mes != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFecha_ini_mes));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fecha_ini_mes cannot be null!!");
                                        }
                                    } if (localFecha_ini_diaTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fecha_ini_dia"));
                                 
                                        if (localFecha_ini_dia != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFecha_ini_dia));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fecha_ini_dia cannot be null!!");
                                        }
                                    } if (localFecha_fin_anhoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fecha_fin_anho"));
                                 
                                        if (localFecha_fin_anho != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFecha_fin_anho));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fecha_fin_anho cannot be null!!");
                                        }
                                    } if (localFecha_fin_mesTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fecha_fin_mes"));
                                 
                                        if (localFecha_fin_mes != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFecha_fin_mes));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fecha_fin_mes cannot be null!!");
                                        }
                                    } if (localFecha_fin_diaTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fecha_fin_dia"));
                                 
                                        if (localFecha_fin_dia != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFecha_fin_dia));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fecha_fin_dia cannot be null!!");
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
        public static MovBilletera parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            MovBilletera object =
                new MovBilletera();

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
                    
                            if (!"movBilletera".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (MovBilletera)us.inswitch.mts.ws.server.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","sesion").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSesion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fecha_ini_anho").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFecha_ini_anho(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fecha_ini_mes").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFecha_ini_mes(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fecha_ini_dia").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFecha_ini_dia(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fecha_fin_anho").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFecha_fin_anho(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fecha_fin_mes").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFecha_fin_mes(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fecha_fin_dia").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFecha_fin_dia(
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
           
          