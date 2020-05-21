/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Square can be translated, scaled, reflected and rotated while remaining a Square. */
final case class Square(width: Double, xCen: Double, yCen: Double, rotation: Angle) extends GeomElemNew
{
  def rotationRadians: Double = rotation.radians
  override def fTrans(f: Vec2 => Vec2): Square = { deb("This is wrong."); Square(width, f(cen), rotation) }
  def cen: Vec2 = xCen vv yCen

  override def slate(offset: Vec2): Square = Square(width, cen + offset)

  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): Square = Square(width, xCen + xOffset, yCen + yOffset, rotation)

  override def scale(operand: Double): Square = Square(width * operand, cen * operand)

  override def mirrorXOffset(yOffset: Double): Square = Square(width, cen.mirrorXOffset(yOffset))

  override def mirrorX: Square = Square(width, xCen, -yCen, rotation)

  override def mirrorYOffset(xOffset: Double): Square = Square(width, cen.mirrorYOffset(xOffset))

  override def mirrorY: Square = Square(width, -xCen, yCen, rotation)

  override def prolign(matrix: ProlignMatrix): Square = Square(width * matrix.vFactor, cen.prolignTrans(matrix), rotation)
}

/** Factory object for squares. */
object Square //extends ShapeIcon
{
 // def apply(width: Double, xCen: Double, yCen: Double, rotationRadians: Double): Square = new Square(width, xCen, yCen, rotationRadians)
  def apply(width: Double, cen: Vec2 = Vec2Z, rotation: Angle = Angle(0)): Square = new Square(width, cen.x, cen.y, rotation)
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