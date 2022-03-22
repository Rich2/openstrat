/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A square aligned to the X and Y axes. */
final class Sqlign private(val unsafeArray: Array[Double]) extends Square with Rect with Show2[Double, Pt2]
{
  override type ThisT = Sqlign
  override def typeStr: String = "Sqlign"
  override def unsafeFromArray(array: Array[Double]): Sqlign = new Sqlign(array)

  def width: Double = v1x - v0x
  //def cenX: Double = (v0x + v2x) / 2
  //def cenY: Double = (v0y + v2y) / 2
  override def name1: String = "width"
  override def name2: String = "cen"
  override def show1: Double = width
  override def show2: Pt2 = cen
  override implicit def showT1: ShowT[Double] = ShowT.doublePersistEv
  override implicit def showT2: ShowT[Pt2] = Pt2.persistImplicit
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
{
  def apply(width: Double, cen: Pt2 = Pt2Z): Sqlign =
  { val cx = cen.x
    val cy = cen.y
    val w = width / 2
    val array = Array[Double](cx - w, cy + w, cx + w, cy + w, cx + w, cy - w, cx - w, cy - w)
    new Sqlign(array)
  }

  def apply(width: Double, xCen: Double, yCen: Double): Sqlign = {
    val w = width / 2
    val array = Array[Double](xCen - w, yCen + w, xCen + w, yCen + w, xCen + w, yCen - w, xCen - w, yCen - w)
    new Sqlign(array)
  }

  implicit val ShowTImplicit: ShowT[Sqlign] = new ShowT[Sqlign]
  { override def typeStr: String = "Sqlign"
    override def strT(obj: Sqlign): String = obj.str
    override def showDecT(obj: Sqlign, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.showDec(way, maxPlaces, 0)
    override def syntaxDepthT(obj: Sqlign): Int = 3
  }
}