/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pWeb

/** An Html Attribute. Every Html attribute is also an XML attribute */
//class HAtt(name: String, value: String) extends XAtt(name, value)

//object HAtt { def apply(name: String, value: String): HAtt = new HAtt(name, value) }

object StyleAtt
{
   def apply(decsIn: CssDec *): StyleAtt = new StyleAtt(decsIn)
}
class StyleAtt(decs: Seq[CssDec]) extends XAtt("style", (decs match
   {
   case Seq() => ""
   case Seq(h, tail @ _*) => (h.out(0) + tail.foldLeft("")(_ -- _.out(0)))
   }))

case class ClickAtt(str: String) extends XAtt("onclick", str)
case class IdAtt(str:String) extends XAtt("id", str)
object HAttJs extends XAtt("type", "text/javascript")

