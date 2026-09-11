/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** A 2-dimensional geometrical object, specified in [[Length]] units. */
trait GeomMetric2Elem extends Any, GeomLen2Elem
{ override def slate(operand: VecPtLen2): GeomMetric2Elem
  override def slate(xOperand: Length, yOperand: Length): GeomMetric2Elem
  override def slateX(xOperand: Length): GeomMetric2Elem
  override def slateY(yOperand: Length): GeomMetric2Elem
  override def scale(operand: Double): GeomMetric2Elem
  override def mapGeom2(operand: Length): Geom2Elem
}

/** A class that can preserve its type through all the [[Prolign]], proportionate XY axes aligned transformations, using a [[Pt2]] => [[Pt2]] function. These
 * are translate [[Slate2]], [[Scale]] and negX and negY, the [[MirrorAxes]], transformations. */
trait ProlignMetric2Preserve extends Any, GeomMetric2Elem
{ /** The most narrow type preserved in some 2d geometric transformations. */
  type ThisT <: ProlignMetric2Preserve

  /** A method to perform all the [[ProlignPreserve]] transformations with a function from PT2 => PT2. */
  def ptsTrans(f: PtLen2 => PtLen2): ThisT

  override def slate(operand: VecPtLen2): ThisT = ptsTrans(_.slate(operand))
  override def slate(xOperand: Length, yOperand: Length): ThisT = ptsTrans(_.slate(xOperand, yOperand))
  override def scale(operand: Double): ThisT = ptsTrans(_.scale(operand))

  //override def negX: ThisT = ptsTrans(_.negX)

  //override def negY: ThisT = ptsTrans(_.negY)

  //override def prolign(matrix: ProlignMatrix): ThisT = ptsTrans(_.prolign(matrix))
}

/** A graphical element in 2 [[Length]] dimension space. */
trait GraphicMetric2Elem extends GeomMetric2Elem
{ override def slate(operand: VecPtLen2): GraphicMetric2Elem
  override def slate(xOperand: Length, yOperand: Length): GraphicMetric2Elem
  override def slateX(xOperand: Length): GraphicMetric2Elem
  override def slateY(yOperand: Length): GraphicMetric2Elem
  override def scale(operand: Double): GraphicMetric2Elem
  override def mapGeom2(operand: Length): Graphic2Elem
}

object GraphicMetric2Elem
{ /** Implicit [[SlateLen2]] type class instance / evidence for [[GraphicMetric2Elem]]. */
  given slateLenEv: SlateLen2[GraphicMetric2Elem] = new SlateLen2[GraphicMetric2Elem]
  { override def slateT(obj: GraphicMetric2Elem, delta: VecPtLen2): GraphicMetric2Elem = obj.slate(delta)
    override def slateXY(obj: GraphicMetric2Elem, xDelta: Length, yDelta: Length): GraphicMetric2Elem = obj.slate(xDelta, yDelta)
    override def slateX(obj: GraphicMetric2Elem, xDelta: Length): GraphicMetric2Elem = obj.slateX(xDelta)
    override def slateY(obj: GraphicMetric2Elem, yDelta: Length): GraphicMetric2Elem = obj.slateY(yDelta)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[GraphicMetric2Elem]]. */
  given scaleEv: Scale[GraphicMetric2Elem] = (obj, operand) => obj.scale(operand)

  /** Implicit [[MapGeom2]] type class instance / evidence for [[GraphicMetric2Elem]] and [[Graphic2Elem]]. */
  given mapGeomEv: MapGeom2[GraphicMetric2Elem, Graphic2Elem] = (obj, operand) => obj.mapGeom2(operand)
}