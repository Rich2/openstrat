/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, wcode.*

object WebXmlCookies1 extends Web6App
{
  override def contents: RArr[XConCompound] = ServletElem.withMapping("Cookies1", "ostrat.pDev.Cookies1")("/")
}