/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

object Arrow
{
  def draw(startPt: Vec2, endPt: Vec2, headAngle: Angle = 30.degs, hypLength: Double = 20, lineWidth: Double = 2,
      lineColour: Colour = Colour.Black): LinesDraw =
  {
    val mainLine = Line2(startPt, endPt)
    val (leftVert, rightVert) = headVerts(startPt, endPt, headAngle, hypLength)    
    val leftLine: Line2 = Line2(endPt, leftVert)    
    val rightLine: Line2 = Line2(endPt, rightVert)
    val segs: Line2s = Line2s(mainLine, leftLine, rightLine)    
    LinesDraw(segs, lineWidth, lineColour)  
  }
  
  def headVerts(startPt: Vec2, endPt: Vec2, headAngle: Angle = 30.degs, hypLength: Double = 20): (Vec2, Vec2) =
  {
    val mainLine = Line2(startPt, endPt)
    val ang: Angle = mainLine.angle
    val leftAng: Angle = ang + 180.degs - headAngle
    val leftVert: Vec2 = leftAng.toVec2 *  hypLength + endPt
    val rightAng: Angle = ang + 180.degs + headAngle
    val rightVert: Vec2 = rightAng.toVec2 * hypLength + endPt
    (leftVert, rightVert)
  }
  
  def apply(startPt: Vec2, endPt: Vec2, headAngle: Angle = 20.degs, hypLength: Double = 25, lineWidth: Double = 2,
      lineColour: Colour = Colour.Black): List[GraphicElem[_]] =
  {    
    val (leftVert, rightVert) = headVerts(startPt, endPt, headAngle, hypLength)
    val shaft = LineDraw(startPt, endPt, lineWidth, lineColour)
    val head = Polygon(rightVert, leftVert, endPt).fill(lineColour)
    List(shaft, head)    
  }
}