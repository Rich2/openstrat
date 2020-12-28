/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A shape based compound graphic. */
trait ShapeCompound extends ShapeGraphic
{ 
  def facets: Arr[GraphicFacet]
  
  /** The [[ShapeCompound]] type will be widened at a later point. */
  def children: Arr[GraphicElem]

  /** 2D geometric translation transformation on this ShapeCompound, returns a ShapeCompound. Return type may be narrowed in sub class /traits. */
  override def xySlate(xOffset: Double, yOffset: Double): ShapeCompound

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeCompound  
  
  /** 2D geometric mirror, reflection transformation across the X axis on this ShapeCompound , returns a ShapeCompound. The return type may be
   *  narrowed in sub trait / classes. */
  override def negY: ShapeCompound

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: ShapeCompound

  override def prolign(matrix: ProlignMatrix): ShapeCompound

  override def rotate90: ShapeCompound

  override def rotate(angle: AngleVec): ShapeCompound

  override def reflect(lineLike: LineLike): ShapeCompound

  override def xyScale(xOperand: Double, yOperand: Double): ShapeCompound
  override def xShear(operand: Double): ShapeCompound

  override def yShear(operand: Double): ShapeCompound
}

/** Companion object for the [[ShapeCompound]] trait, contains implicit instances for 2D geometric transoframtion type classes for common collection
 *  and other containner classes. */
object ShapeCompound
{
  implicit val slateImplicit: Slate[ShapeCompound] = (obj: ShapeCompound, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[ShapeCompound] = (obj: ShapeCompound, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeCompound] = (obj: ShapeCompound, angle: AngleVec) => obj.rotate(angle)

  implicit val reflectAxesImplicit: ReflectAxes[ShapeCompound] = new ReflectAxes[ShapeCompound]
  { override def negYT(obj: ShapeCompound): ShapeCompound = obj.negY
    override def negXT(obj: ShapeCompound): ShapeCompound = obj.negX
    override def rotate90(obj: ShapeCompound): ShapeCompound = obj.rotate90
  }

  implicit val prolignImplicit: Prolign[ShapeCompound] = (obj, matrix) => obj.prolign(matrix)  
}