/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait WebApp extends XmlMulti
{
  override def tag: String = "web-app"
}

object JakartaNs extends XmlNs("https://jakarta.ee/xml/ns/jakartaee")
object JakartaXsi extends XmlNsXsi("http://www.w3.org/2001/XMLSchema-instance")