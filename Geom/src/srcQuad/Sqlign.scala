/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.Colour.Black

/** A square aligned to the X and Y axes. So these squares can be defined by their widths and their centre points. However, the position of the vertices 0, 1, 2
 * and 3 are not fixed. they can be changed by rotations and reflections. The clockwise, anticlockwise ordering of the vertices can be changed by reflections.
 * The convention is for vertex 0 to be left top but this can change. */
final class Sqlign private(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends Square, Rect,
  Tell2[Double, Pt2]
{ override type ThisT = Sqlign
  override def typeStr: String = "Sqlign"  
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
  override def slate(operand: VecPt2): Sqlign = slate(operand.x, operand.y)
  
  override def slate(xOperand: Double, yOperand: Double): Sqlign =
    new Sqlign(v0x + xOperand, v0y + yOperand, v1x + xOperand, v1y + yOperand, v2x + xOperand, v2y + yOperand)

  override def slateFrom(operand: VecPt2): Sqlign = slateFrom(operand.x, operand.y)

  override def slateFrom(xOperand: Double, yOperand: Double): Sqlign =
    new Sqlign(v0x - xOperand, v0y - yOperand, v1x - xOperand, v1y - yOperand, v2x - xOperand, v2y - yOperand)  
    
  override def slateX(xOperand: Double): Sqlign = new Sqlign(v0x + xOperand, v0y, v1x + xOperand, v1y, v2x + xOperand, v2y)
  override def slateY(yOperand: Double): Sqlign = new Sqlign(v0x, v0y + yOperand, v1x, v1y + yOperand, v2x, v2y + yOperand)
  override def scale(operand: Double): Sqlign = new Sqlign(v0x * operand, v0y * operand, v1x * operand, v1y + operand, v2x * operand, v2y * operand)
  override def negX: Sqlign = new Sqlign(-v0x, v0y, -v1x, v1y, -v2x, v2y)
  override def negY: Sqlign = new Sqlign(v0x, -v0y, v1x, -v1y, v2x, -v2y)
  override def rotate90: Sqlign = new Sqlign(-v0y, v0x, -v1y, v1x, -v2y, v2x)
  override def rotate180: Sqlign = new Sqlign(-v0x, -v0y, -v1x, -v1y, -v2x, -v2y)
  override def rotate270: Sqlign = new Sqlign(v0y, v0x, v1y, -v1x, v2y, -v2x)
  override def prolign(matrix: AxlignMatrix): Sqlign = Sqlign.from3(v0.prolign(matrix), v1.prolign(matrix), v2.prolign(matrix))
  override def addMargin(delta: Double): Sqlign = ??? // Sqlign(width + 2 * delta, cenX, cenY, vertOrder)
 
  override def diagRectangles(childWidth: Double): RArr[Rectangle] =
  { val r1 = Rect(diagLen, childWidth, cen)
    RArr(r1.rotate45About(r1.cen), r1.clk45About(r1.cen))
  }

  override def diagLen: Double = (v0x - v2x).abs * 2.sqrt
  override def fill(fillfacet: FillFacet): SqlignFill = SqlignFill(this, fillfacet)
  override def fillInt(intValue: Int): SqlignFill = SqlignFill(this, Colour(intValue))
  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): SqlignDraw = SqlignDraw(this, lineWidth, lineColour)
}

/** Companion object for [[Sqlign]] class, a square aligned to the X and Y axes. Contains factory apply methods. */
object Sqlign
{ /** Factory apply method to construct a square aligned to the Xand Y axes, from its width and centre point. There are apply name overloads to specify the
   * centre position by its X and Y components. */
  def apply(width: Double, cen: Pt2 = Origin2, vertOrder: Int = 0): Sqlign =
  { val d = width / 2
    new Sqlign(cen.x + d, cen.y + d, cen.x + d, cen.y - d, cen.x - d, cen.y - d)
  }

  /** Factory apply method to construct a square aligned to the Xand Y axes, from its width and centre point. There is an apply name overloads to specify the
   * centre position by a single [[Pt2]] parameter and a name overload for the rare occasions when you need to change the vertex order from the default. */
  def apply(width: Double, cenX: Double, cenY: Double): Sqlign =
  { val d = width / 2
    new Sqlign(cenX + d, cenY + d, cenX + d, cenY - d, cenX - d, cenY - d)
  }  

  /** Factory apply method to construct a square aligned to the Xand Y axes, from its width and centre point. There are apply name overloads to specify the
   * centre position by a single [[Pt2]] parameter and a name overload with the default vertex order. */
  def apply(width: Double, cenX: Double, cenY: Double, vertOrder: Int): Sqlign =
  { val d = width / 2
    new Sqlign(cenX + d, cenY + d, cenX + d, cenY - d, cenX - d, cenY - d)
  }

  /** Factory method for constructing [[Sqlign]] from the first three vertices. */
  def from3(v0: Pt2, v1: Pt2, v2: Pt2): Sqlign = new Sqlign(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)

  /** Factory method for constructing [[Sqlign]] from the first three vertices, given as X and Y parameters. */
  def from3(v0x: Double, v0y: Double, v1x: Double, v1y: Double, v2x: Double, v2y: Double): Sqlign = new Sqlign(v0x, v0y, v1x, v1y, v2x, v2y)

  given showEv: Show[Sqlign] = new Show[Sqlign]
  { override def typeStr: String = "Sqlign"
    override def strT(obj: Sqlign): String = obj.str
    override def show(obj: Sqlign, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.tell(way, maxPlaces, 0)
    override def syntaxDepth(obj: Sqlign): Int = 3
  }

  /** Implicit [[Slate2]] type class instance / evidence for [[Sqlign]]. */
  given slate2Ev: Slate2[Sqlign] = new Slate2[Sqlign]
  { override def slateT(obj: Sqlign, operand: VecPt2): Sqlign = obj.slate(operand)
    override def slateXY(obj: Sqlign, xOperand: Double, yOperand: Double): Sqlign = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: Sqlign, operand: VecPt2): Sqlign = obj.slateFrom(operand)
    override def slateFromXY(obj: Sqlign, xOperand: Double, yOperand: Double): Sqlign = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: Sqlign, xOperand: Double): Sqlign = obj.slateX(xOperand)
    override def slateY(obj: Sqlign, yOperand: Double): Sqlign = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[Sqlign]]. */
  given scaleEv: Scale[Sqlign] = (obj: Sqlign, operand: Double) => obj.scale(operand)

  /** Implicit [[Prolign]] type class instance / evidence for [[Sqlign]]. */
  given prolignEv: Prolign[Sqlign] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Drawing]] type class instance / evidence for [[Sqlign]]. */
  given drawingEv: Drawing[Sqlign, SqlignDraw] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)

  /** Implicit [[Filling]] type class evidence for [[Sqlign]]. */
  given fillingEv: Filling[Sqlign, SqlignFill] = (obj, fillFactet) => obj.fill(fillFactet)
}