/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

class Square(val xCen: Double, val yCen: Double, val width: Double, rotationRadians: Double) extends TransSimer
{ override type AlignT = Square
  def cen: Vec2 = xCen vv yCen
  def cenRight: Vec2 = cen + rotation.toVec2(width / 2)
  def rotation: Angle = Angle(rotationRadians)
  override def mirror(line: Line2): Square = Square(cen.mirror(line), width, rotation)
  override def slate(offset: Vec2): Square = Square(cen + offset, width, rotation)
  override def scale(operand: Double): Square = Square(cen * operand, width * operand, rotation)

  override def rotateRadians(radians: Double): Square =
  { val newCen = cen.rotateRadians(radians)
    val newCenRight = cenRight.rotateRadians(radians)
    val newAngle = (newCenRight - newCen).angle
    Square(newCen, width, newAngle)
  }

  override def shear(xScale: Double, yScale: Double): TransAffer = ???
}

/** Factory object for squares. There is no companion Square class. */
object Square //extends UnScaledPolygon
{
  def apply(cen: Vec2, width: Double, rotation: Angle = Angle(0)): Square = new Square(cen.x, cen.y, width, rotation.radians)
  //val apply: Polygon = Polygon(0.5 vv 0.5, 0.5 vv -0.5, -0.5 vv -0.5, -0.5 vv 0.5)
  //def apply(width: Double = 1, cen: Vec2 = Vec2Z): Polygon = apply.fTrans(_ * width + cen)
  def xy(width: Double, xCen: Double, yCen: Double): PolygonGen = PolygonGen(
      xCen - width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen - width / 2,
      xCen - width/2   vv yCen - width / 2)
   
  /**Needs Changing possibly removing. */
 // def fill(colour: Colour)/*, width: Double, cen: Vec2 = Vec2Z)*/: PolygonFill = apply.fill(colour)
  /**Needs Changing possibly removing. */
 // def fillXY(width: Double, colour: Colour, xCen: Double, yCen: Double): PolygonFill = apply(width, xCen vv yCen).fill(colour)
   
  def curvedSegs(width: Double, radius: Double): List[CurveSeg] =
  { val w = width / 2
    (0 to 3).toList.flatMap(i => List( LineSeg(w - radius, w), ArcSeg(w - radius vv w -radius, w vv w - radius)).rotateRadians(-i * math.Pi / 2))
  }
}