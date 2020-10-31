/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A shape based compound graphic. */
trait ShapeCompound extends ShapeGraphic
{ 
  def facets: Arr[GraphicFacet]
  
  /** The [[ShapeCompound]] type will be widened at a later point. */
  def children: Arr[GraphicElem]

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): ShapeCompound

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): ShapeCompound

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeCompound  
  
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: ShapeCompound

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: ShapeCompound


  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a ShapeCompound, returns a ShapeCompound. The
   * return type will be narrowed in sub traits / classes. */
  /*override def rotate90: ShapeCompound = ???

  /** Rotate 180 degrees 2D geometric transformation on a ShapeCompound, returns a ShapeCompound. The return type will be narrowed in sub traits /
   * classes. */
  override def rotate180: ShapeCompound = ???

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a ShapeCompound, returns a ShapeCompound. The
   * return type will be narrowed in sub traits / classes. */
  override def rotate270: ShapeCompound = ???*/

  override def prolign(matrix: ProlignMatrix): ShapeCompound

  override def rotate(angle: Angle): ShapeCompound

  override def reflect(lineLike: LineLike): ShapeCompound

  override def xyScale(xOperand: Double, yOperand: Double): ShapeCompound
  override def xShear(operand: Double): ShapeCompound

  override def yShear(operand: Double): ShapeCompound

 // override def reflect(line: LineSeg): ShapeCompound
}

/** Companion object for the [[ShapeCompound]] trait, contains implicit instances for 2D geometric transoframtion type classes for common collection
 *  and other containner classes. */
object ShapeCompound
{
  implicit val slateImplicit: Slate[ShapeCompound] = (obj: ShapeCompound, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[ShapeCompound] = (obj: ShapeCompound, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeCompound] = (obj: ShapeCompound, angle: Angle) => obj.rotate(angle)

  implicit val reflectAxesImplicit: ReflectAxes[ShapeCompound] = new ReflectAxes[ShapeCompound]
  { override def negYT(obj: ShapeCompound): ShapeCompound = obj.negY
    override def negXT(obj: ShapeCompound): ShapeCompound = obj.negX
    /*override def rotate90T(obj: ShapeCompound): ShapeCompound = obj.rotate90
    override def rotate180T(obj: ShapeCompound): ShapeCompound = obj.rotate180
    override def rotate270T(obj: ShapeCompound): ShapeCompound = obj.rotate270*/
  }

  implicit val prolignImplicit: Prolign[ShapeCompound] = (obj, matrix) => obj.prolign(matrix)  
}