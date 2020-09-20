/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

object Arrow
{
  def draw(startPt: Vec2, endPt: Vec2, headAngle: Angle = 30.degs, hypLength: Double = 20, lineWidth: Double = 2, lineColour: Colour = Colour.Black):
    LinesDraw =
  {
    val mainLine = LineSeg(startPt, endPt)
    val (leftVert, rightVert) = headVerts(startPt, endPt, headAngle, hypLength)    
    val leftLine: LineSeg = LineSeg(endPt, leftVert)
    val rightLine: LineSeg = LineSeg(endPt, rightVert)
    val segs: LineSegs = LineSegs(mainLine, leftLine, rightLine)
    LinesDraw(segs, lineWidth, lineColour)
  }
  
  def headVerts(startPt: Vec2, endPt: Vec2, headAngle: Angle = 30.degs, hypLength: Double = 20): (Vec2, Vec2) =
  {
    val mainLine = LineSeg(startPt, endPt)
    val ang: Angle = mainLine.angle
    val leftAng: Angle = ang + 180.degs - headAngle
    val leftVert: Vec2 = leftAng.toVec2(hypLength) + endPt
    val rightAng: Angle = ang + 180.degs + headAngle
    val rightVert: Vec2 = rightAng.toVec2(hypLength) + endPt
    (leftVert, rightVert)
  }
  
  def apply(startPt: Vec2, endPt: Vec2, headAngle: Angle = 20.degs, hypLength: Double = 25, lineWidth: Double = 2,
            lineColour: Colour = Colour.Black): Arr[DisplayAffineElem] =
  {    
    val (leftVert, rightVert) = headVerts(startPt, endPt, headAngle, hypLength)
    val shaft = LineDraw(startPt, endPt, lineWidth, lineColour)
    val head = PolygonGen(rightVert, leftVert, endPt).fillOld(lineColour)
    Arr(shaft, head)
  }
}