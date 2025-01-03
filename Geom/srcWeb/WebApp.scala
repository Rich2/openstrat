/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** web.xml file for a Servlet. */
trait WebApp extends XmlMulti
{ override def tag: String = "web-app"
}

/** web.xml file for a Servlet 6.0. */
trait WebApp6 extends WebApp
{ override def attribs: RArr[XmlAtt] = RArr(JakartaNs, JakartaXsi, JakartaLoc6, VersionAtt("6.0"), MetadataCompleted)
}

/** Servlet XML element for Jakarta. */
case class ServletElem(contents: RArr[XCon]) extends XmlMultiNoAtts
{ override def tag: String = "servlet"
}

object ServletElem
{
  def apply(name: String, servletClass: String, otherElems: XCon*): ServletElem =
    new ServletElem(RArr(ServletName(name), ServletClass(servletClass)) ++ otherElems.toArr)
}

/** Servlet-name XML element for Jakarta. */
class ServletName(value: String) extends XmlElemSimple("servlet-name", value)

/** Servlet-class XML element for Jakarta. */
class ServletClass(value: String) extends XmlElemSimple("servlet-class", value)

/** The Jakarta XML namespace. */
object JakartaNs extends Xmlns("https://jakarta.ee/xml/ns/jakartaee")

/** The Jakarta XML schema. */
object JakartaXsi extends XmlNsXsi("http://www.w3.org/2001/XMLSchema-instance")

/** The Jakarta XML schema location. */
object JakartaLoc6 extends XsiSchemaLoc("""https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd""")

/** metadata-complete="true" */
object MetadataCompleted extends XmlAttGen("metadata-complete", "true")