/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

object Arrow
{
  def draw(startPt: Pt2, endPt: Pt2, headAngle: Angle = 30.degs, hypLength: Double = 20, lineWidth: Double = 2, lineColour: Colour = Colour.Black):
    LinesDraw =
  {
    val mainLine = LineSeg(startPt, endPt)
    val (leftVert, rightVert) = headVerts(startPt, endPt, headAngle, hypLength)    
    val leftLine: LineSeg = LineSeg(endPt, leftVert)
    val rightLine: LineSeg = LineSeg(endPt, rightVert)
    val segs: LineSegs = LineSegs(mainLine, leftLine, rightLine)
    LinesDraw(segs, lineWidth, lineColour)
  }
  
  def headVerts(startPt: Pt2, endPt: Pt2, headAngle: Angle = 30.degs, hypLength: Double = 20): (Pt2, Pt2) =
  {
    val mainLine = LineSeg(startPt, endPt)
    val ang: Angle = mainLine.angle
    val leftAng: Angle = ang + 180.degs - headAngle
    val leftVert: Pt2 = leftAng.toVec2(hypLength) + endPt
    val rightAng: Angle = ang + 180.degs + headAngle
    val rightVert: Pt2 = rightAng.toVec2(hypLength) + endPt
    (leftVert, rightVert)
  }
  
  def apply(startPt: Pt2, endPt: Pt2, headAngle: Angle = 20.degs, hypLength: Double = 25, lineWidth: Double = 2,
            lineColour: Colour = Colour.Black): Arr[GraphicElem] =
  {    
    val (leftVert, rightVert) = headVerts(startPt, endPt, headAngle, hypLength)
    val shaft = LineSegDraw(startPt, endPt, lineColour, lineWidth)
    val head = PolygonImp(rightVert, leftVert, endPt).fill(lineColour)
    Arr(shaft, head)
  }
}