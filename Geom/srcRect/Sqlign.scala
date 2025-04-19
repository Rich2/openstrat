/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A square aligned to the X and Y axes. So these squares can be defined by their widths and their centre points. However, the postion of the vertices 0, 1, 2
 * and 3 are not fixed. they can be changed by rotations and reflections. The clockwise, anticlockwise ordering of the vertices can be changed by reflections.
 * The convention is for vertex 0 to be left top but this can change. */
final class Sqlign private(val width: Double, val cenX: Double, val cenY: Double, val vertOrder: Int) extends Square, Rect, Tell2[Double, Pt2]
{ override type ThisT = Sqlign
  override def typeStr: String = "Sqlign"

  override def fromArray(array: Array[Double]): Sqlign = ??? // new Sqlign(array)
  override def name1: String = "width"
  override def name2: String = "cen"
  override def tell1: Double = width
  override def tell2: Pt2 = cen
  override implicit def show1: Show[Double] = Show.doubleEv
  override implicit def show2: Show[Pt2] = Pt2.persistEv
  override def tellDepth: Int = 3
  //  override def attribs: RArr[XmlAtt] = ???
  override def width1: Double = width
  override def width2: Double = width
  override def height: Double = width
  override def slate(operand: VecPt2): Sqlign = Sqlign(width, cen.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): Sqlign = Sqlign(width, cenX + xOperand, cenY + yOperand)
  override def slateX(xOperand: Double): Sqlign = Sqlign(width, cenX + xOperand, cenY)
  override def slateY(yOperand: Double): Sqlign = Sqlign(width, cenX, cenY + yOperand)
  override def scale(operand: Double): Sqlign = Sqlign(width * operand, cen.scale(operand))
  override def negX: Sqlign = Sqlign(width, -cenX, cenY)
  override def negY: Sqlign = Sqlign(width, cenX, -cenY)
  override def rotate90: Sqlign = Sqlign(width, cen.rotate90)
  override def rotate180: Sqlign = Sqlign(width, cen.rotate180)
  override def rotate270: Sqlign = Sqlign(width, cen.rotate270)
  override def prolign(matrix: ProlignMatrix): Sqlign = Sqlign(width * matrix.vFactor, cen.prolign(matrix))

  /** Adds a margin to this [[Sqlign]], square aligned with the XY axes, moving the sides out by the given parameter. */
  override def addMargin(delta: Double): Sqlign = Sqlign(width + 2 * delta, cenX, cenY)

  override def elemFromDbls(d1: Double, d2: Double): Pt2 = ???

  override def vertX(index: Int): Double = arrayUnsafe(index * 2)
  override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)

  override def unsafeNegX: Array[Double] = arrayD1Map(d => -d)
  override def unsafeNegY: Array[Double] = arrayD2Map(d => -d)
}

/** Companion object for [[Sqlign]] class, a square aligned to the X and Y axes. Contains factory apply methods. */
object Sqlign
{
  def apply(width: Double, cen: Pt2 = Pt2Z): Sqlign = new Sqlign(width, cen.x, cen.y, 0)


  def apply(width: Double, cenX: Double, cenY: Double): Sqlign = new Sqlign(width, cenX, cenY, 0)
  

  implicit val showEv: Show[Sqlign] = new Show[Sqlign]
  { override def typeStr: String = "Sqlign"
    override def strT(obj: Sqlign): String = obj.str
    override def show(obj: Sqlign, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.tell(way, maxPlaces, 0)
    override def syntaxDepth(obj: Sqlign): Int = 3
  }
}