/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pWeb

sealed trait HtmlVoid extends HtmlEl with XmlVoid
{
   override def startTagClose = ">"
}


case object HtmlBr extends HtmlVoid
{
   override def tag = "br"
}

case class CssLink(name: String) extends HtmlVoid
{
   def tag = "link"
   override def atts = Seq(XAtt("rel", "stylesheet"), XAtt("type", "text/css"), XAtt("href", name + ".css"))
}
case class HLink(addr: String, label: String) extends HNotVoid
{
   def tag = "a"
   override def atts = Seq(XAtt("href", addr))
   override def mems: Seq[XCon] = Seq(IndText(label))
}
trait HInput extends HtmlVoid { def tag = "input" }
case class HImg(srcStr: String, altStr: String) extends HtmlVoid
{
   def tag = "img"
   override def atts = Seq(XAtt("src", srcStr), XAtt("alt", altStr))
}

trait HMeta extends HtmlVoid { def tag = "meta" }