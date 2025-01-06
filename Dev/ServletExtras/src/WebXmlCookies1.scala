/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*

object WebXmlCookies1 extends WebApp6
{
  override def contents: RArr[XCon] = ServletElem.withMapping("Cookies1", "ostrat.pDev.Cookies1")("/")
}