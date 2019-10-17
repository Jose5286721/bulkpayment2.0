
/**
 * BilleteraPack.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:19:26 CET)
 */
            
                package us.inswitch.mts.ws.server;
            

            /**
            *  BilleteraPack bean class
            */
        
        public  class BilleteraPack
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = billeteraPack
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
                        * field for Estado
                        */

                        
                                    protected java.lang.String localEstado ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEstadoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEstado(){
                               return localEstado;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Estado
                               */
                               public void setEstado(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localEstadoTracker = true;
                                       } else {
                                          localEstadoTracker = false;
                                              
                                       }
                                   
                                            this.localEstado=param;
                                    

                               }
                            

                        /**
                        * field for FechaVencimiento
                        */

                        
                                    protected java.lang.String localFechaVencimiento ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFechaVencimientoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFechaVencimiento(){
                               return localFechaVencimiento;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FechaVencimiento
                               */
                               public void setFechaVencimiento(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localFechaVencimientoTracker = true;
                                       } else {
                                          localFechaVencimientoTracker = false;
                                              
                                       }
                                   
                                            this.localFechaVencimiento=param;
                                    

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
                        * field for SaldoMaximo
                        */

                        
                                    protected java.math.BigDecimal localSaldoMaximo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSaldoMaximoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getSaldoMaximo(){
                               return localSaldoMaximo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SaldoMaximo
                               */
                               public void setSaldoMaximo(java.math.BigDecimal param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localSaldoMaximoTracker = true;
                                       } else {
                                          localSaldoMaximoTracker = false;
                                              
                                       }
                                   
                                            this.localSaldoMaximo=param;
                                    

                               }
                            

                        /**
                        * field for SaldoMinimo
                        */

                        
                                    protected java.math.BigDecimal localSaldoMinimo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSaldoMinimoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getSaldoMinimo(){
                               return localSaldoMinimo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SaldoMinimo
                               */
                               public void setSaldoMinimo(java.math.BigDecimal param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localSaldoMinimoTracker = true;
                                       } else {
                                          localSaldoMinimoTracker = false;
                                              
                                       }
                                   
                                            this.localSaldoMinimo=param;
                                    

                               }
                            

                        /**
                        * field for Sobregiro
                        */

                        
                                    protected java.math.BigDecimal localSobregiro ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSobregiroTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getSobregiro(){
                               return localSobregiro;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Sobregiro
                               */
                               public void setSobregiro(java.math.BigDecimal param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localSobregiroTracker = true;
                                       } else {
                                          localSobregiroTracker = false;
                                              
                                       }
                                   
                                            this.localSobregiro=param;
                                    

                               }
                            

                        /**
                        * field for TipoBilletera
                        */

                        
                                    protected java.lang.String localTipoBilletera ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTipoBilleteraTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTipoBilletera(){
                               return localTipoBilletera;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TipoBilletera
                               */
                               public void setTipoBilletera(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localTipoBilleteraTracker = true;
                                       } else {
                                          localTipoBilleteraTracker = false;
                                              
                                       }
                                   
                                            this.localTipoBilletera=param;
                                    

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
                       BilleteraPack.this.serialize(parentQName,factory,xmlWriter);
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
                           namespacePrefix+":billeteraPack",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "billeteraPack",
                           xmlWriter);
                   }

               
                   }
                if (localEstadoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"estado", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"estado");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("estado");
                                    }
                                

                                          if (localEstado==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("estado cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEstado);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFechaVencimientoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"fechaVencimiento", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"fechaVencimiento");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("fechaVencimiento");
                                    }
                                

                                          if (localFechaVencimiento==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fechaVencimiento cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFechaVencimiento);
                                            
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
                             } if (localSaldoMaximoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"saldoMaximo", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"saldoMaximo");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("saldoMaximo");
                                    }
                                

                                          if (localSaldoMaximo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("saldoMaximo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSaldoMaximo));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSaldoMinimoTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"saldoMinimo", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"saldoMinimo");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("saldoMinimo");
                                    }
                                

                                          if (localSaldoMinimo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("saldoMinimo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSaldoMinimo));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSobregiroTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"sobregiro", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"sobregiro");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("sobregiro");
                                    }
                                

                                          if (localSobregiro==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("sobregiro cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSobregiro));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTipoBilleteraTracker){
                                    namespace = "";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"tipoBilletera", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"tipoBilletera");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("tipoBilletera");
                                    }
                                

                                          if (localTipoBilletera==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("tipoBilletera cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTipoBilletera);
                                            
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

                 if (localEstadoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "estado"));
                                 
                                        if (localEstado != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEstado));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("estado cannot be null!!");
                                        }
                                    } if (localFechaVencimientoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "fechaVencimiento"));
                                 
                                        if (localFechaVencimiento != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFechaVencimiento));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fechaVencimiento cannot be null!!");
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
                                    } if (localSaldoMaximoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "saldoMaximo"));
                                 
                                        if (localSaldoMaximo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSaldoMaximo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("saldoMaximo cannot be null!!");
                                        }
                                    } if (localSaldoMinimoTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "saldoMinimo"));
                                 
                                        if (localSaldoMinimo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSaldoMinimo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("saldoMinimo cannot be null!!");
                                        }
                                    } if (localSobregiroTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "sobregiro"));
                                 
                                        if (localSobregiro != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSobregiro));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("sobregiro cannot be null!!");
                                        }
                                    } if (localTipoBilleteraTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "tipoBilletera"));
                                 
                                        if (localTipoBilletera != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTipoBilletera));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("tipoBilletera cannot be null!!");
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
        public static BilleteraPack parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            BilleteraPack object =
                new BilleteraPack();

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
                    
                            if (!"billeteraPack".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (BilleteraPack)us.inswitch.mts.ws.server.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","estado").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEstado(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","fechaVencimiento").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFechaVencimiento(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","saldoMaximo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSaldoMaximo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","saldoMinimo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSaldoMinimo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","sobregiro").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSobregiro(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","tipoBilletera").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTipoBilletera(
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
           
          