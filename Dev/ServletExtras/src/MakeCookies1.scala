/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, wcode.*, webjvm.*

object MakeCookies1 extends ServletUtil
{
  override def name: String = "Tommy"
  
  def main(args: Array[String]): Unit = procArgs(args)
}

object WebXmlCookies1 extends Web6App
{
  override def contents: RArr[XConCompound] = ServletElem.withMapping("Cookies1", "ostrat.pDev.Cookies1")("/")
}