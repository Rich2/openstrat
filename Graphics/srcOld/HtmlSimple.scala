/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pWeb

sealed trait HSimple extends HNotVoid with XmlSimple

case class TabTitle(str: String) extends HSimple { def tag = "title" }
case class HtmlH1(str : String) extends HSimple { def tag = "h1" }
case class HtmlH2(str : String) extends HSimple { def tag = "h2" }
case class HtmlH3(str : String) extends HSimple { def tag = "h3" }
case class HLItem(str: String) extends HSimple { def tag = "li" }

object HNoScript extends HSimple
{
   def tag = "noscript"
   def str = "This page will not function properly without Javascript enabled"
}
case class HButt(str: String, extraAtts: XAtt *) extends HSimple
{
   def tag = "button"
   override def atts = super.atts ++ extraAtts
}

case class PButt(str: String) extends HSimple
{
   def tag = "button"
   override def atts = Seq(ClickAtt("askServer('" + str + "')"), StyleAtt(CssFontCent(300)))
}

object HPre
{
   def r(str: String)(attsIn: XAtt*): HPre = new HPre(str)
   {
      override def atts: Seq[XAtt] = attsIn
   }
}

case class HPre(str: String) extends HSimple { def tag = "pre" }
case class HCode(str: String) extends HSimple
{
   def tag = "code"
   override def out(indent: Int) = openAtts(indent).nl + str.nli + closeTag
}

