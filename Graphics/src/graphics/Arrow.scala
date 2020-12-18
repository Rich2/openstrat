/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

object Arrow
{
  def draw(startPt: Pt2, endPt: Pt2, headAngle: AngleVec = Deg25, hypLength: Double = 20, colour: Colour = Black, lineWidth: Double = 2):
    GraphicElems =
  {
    val mainLine = LineSeg(startPt, endPt)
    val (leftVert, rightVert) = headVerts(startPt, endPt, headAngle, hypLength)    
//    val leftLine: LineSeg = LineSeg(endPt, leftVert)
//    val rightLine: LineSeg = LineSeg(endPt, rightVert)
//    val segs: LineSegs = LineSegs(mainLine, leftLine, rightLine)
//    LinesDraw(segs, lineWidth, lineColour)
    Arr(mainLine.draw(colour, lineWidth), Triangle(leftVert, endPt, rightVert).fill(colour))
  }
  
  def headVerts(startPt: Pt2, endPt: Pt2, headAngle: AngleVec = Deg25, hypLength: Double = 20): (Pt2, Pt2) =
  {
    val mainLine = LineSeg(startPt, endPt)
    val hl2 = hypLength.min(mainLine.length / 2)
    val ang: Angle = mainLine.angle
    val leftAng: Angle = ang + 180.degs - headAngle
    val leftVert: Pt2 = endPt + leftAng.toVec2(hl2)
    val rightAng: Angle = ang + 180.degs + headAngle
    val rightVert: Pt2 = endPt + rightAng.toVec2(hl2)
    (leftVert, rightVert)
  }
  
  def apply(startPt: Pt2, endPt: Pt2, headAngle: AngleVec = Deg25, hypLength: Double = 25, lineWidth: Double = 2,
            lineColour: Colour = Colour.Black): Arr[GraphicElem] =
  {    
    val (leftVert, rightVert) = headVerts(startPt, endPt, headAngle, hypLength)
    val shaft = LineSegDraw(startPt, endPt, lineColour, lineWidth)
    val head = PolygonImp(rightVert, leftVert, endPt).fill(lineColour)
    Arr(shaft, head)
  }
}