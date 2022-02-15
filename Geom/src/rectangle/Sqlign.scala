/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A square aligned to the X and Y axes. */
final case class Sqlign private(width: Double, cenX: Double, cenY: Double) extends Square with Rect with Show2[Double, Pt2]
{
  override def typeStr: String = "Sqlign"
  override def name1: String = "width"
  override def name2: String = "cen"
  override def show1: Double = width
  override def show2: Pt2 = cen
  override implicit def showT1: ShowTDec[Double] = ShowTDec.doublePersistImplicit
  override implicit def showT2: ShowTDec[Pt2] = Pt2.persistImplicit
  override def syntaxDepth: Int = 3
  override def attribs: Arr[XANumeric] = ???
  override def width1 = width
  override def width2: Double = width
  override def height: Double = width
  override def rotation: AngleVec = 0.degs
  override def slate(offset: Vec2Like): Sqlign = Sqlign(width, cen.slate(offset))

  override def slateXY(xDelta: Double, yDelta: Double): Sqlign = Sqlign(width, cenX + xDelta, cenY + yDelta)
  override def scale(operand: Double): Sqlign = Sqlign(width * operand, cen.scale(operand))

  override def negY: Sqlign = Sqlign(width, cenX, -cenY)

  override def negX: Sqlign = Sqlign(width, -cenX, cenY)

  override def rotate90: Sqlign = Sqlign(width, cen.rotate90)
  override def rotate180: Sqlign = Sqlign(width, cen.rotate180)
  override def rotate270: Sqlign = Sqlign(width, cen.rotate270)

  override def prolign(matrix: ProlignMatrix): Sqlign = Sqlign(width * matrix.vFactor, cen.prolign(matrix))
}

/** Companion object for Sqlign class, a square aligned to the X and Y axes. Contains factory apply methods. */
object Sqlign
{ def apply(width: Double, cen: Pt2 = Pt2Z): Sqlign = new Sqlign(width, cen.x, cen.y)
  def apply(width: Double, xCen: Double, yCen: Double): Sqlign = new Sqlign(width, xCen, yCen)

  implicit val ShowTImplicit: ShowTDec[Sqlign] = new ShowTDec[Sqlign]
  { override def typeStr: String = "Sqlign"
    override def strT(obj: Sqlign): String = obj.str
    override def showDecT(obj: Sqlign, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.show(way, maxPlaces, 0)
    override def syntaxDepthT(obj: Sqlign): Int = 3
  }
}