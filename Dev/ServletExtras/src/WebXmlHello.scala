/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, wcode.*

object WebXmlHello extends Web6App1
{
  override def contents: RArr[XConCompound] = ServletElem.withMapping("Hello", "ostrat.pDev.HelloServlet")("/")
}