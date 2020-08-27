/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pWeb

trait SvgEl extends XmlEl// with SvgCon
{  
   def svgAtts: Seq[SvgAtt] = Seq[SvgAtt]()
   override def atts: Seq[XAtt] = svgAtts
}

trait SvgVoid extends SvgEl with XmlVoid

trait SvgCompound extends SvgEl with XNotVoid

object SvgGroup
{
   def apply(attsIn: XAtt*)(sMemsIn: XCon*): SvgGroup = new SvgGroup
   {
      override val atts: Seq[XAtt] = attsIn
      override val sMems: Seq[XCon] = sMemsIn   
   }
}
trait SvgGroup extends SvgCompound
{
   override def tag: String = "g"
   val sMems: Seq[XCon] 
   override def mems = sMems
}

case class SvgCircle(cx: Int, cy: Int, radius:Int, fillColour: Colour) extends SvgVoid
{
   override def tag: String = "circle"
   override def atts: Seq[SvgAtt] = Seq(SvgAtt("cx", cx.toString), SvgAtt("cy", cy.toString), SvgAtt("r", radius.toString), SvgAtt("fill", fillColour.hexStr))   
}

case class SvgRect(x: Int, y: Int, width: Int, height: Int, fillColour: Colour) extends SvgVoid
{
   override def tag: String = "rect"
   override def atts: Seq[XAtt] = Seq(
         XAtt("width", width.toString),
         XAtt("height", height.toString),
         XAtt("x", x.toString),
         XAtt("y", y.toString),
         SvgAtt("fill", fillColour.hexStr))    
}

case class SvgSquare(x: Int, y: Int, width: Int, fillColour: Colour) extends SvgVoid
{
   override def tag: String = "rect"
   override def atts: Seq[XAtt] = Seq(
         XAtt("width", width.toString),
         XAtt("height", width.toString),
         XAtt("x", x.toString),
         XAtt("y", y.toString),
         SvgAtt("fill", fillColour.hexStr))    
}