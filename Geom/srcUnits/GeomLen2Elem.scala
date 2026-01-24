/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** A 2-dimensional geometrical object, specified in [[Length]] units. */
trait GeomLen2Elem extends Any
{ /** Translate 2 [[Length]] dimension geometric transformation [[GeomLen2Elem]]. The Return type will be narrowed in sub traits. There is a name overload
   *  taking the X and Y Deltas as separate parameters, slateX and slateY methods are also available. */
  def slate(operand: VecPtLen2): GeomLen2Elem

  /** Translate 2 [[Length]] dimension geometric transformation, taking the xDelta and yDelta as parameters on this [[GeomLen2Elem]] returning a
   * [[GeomLen2Elem]]. The Return type will be narrowed in sub traits. There is a name overload method taking a [[Pt2]] or [[Vec2]] as a parameter, slateX and
   * slateY methods are also available. */
  def slate(xOperand: Length, yOperand: Length): GeomLen2Elem

  /** Translate 2 [[Length]] dimension geometric transformation in the X dimension returning a [[GeomLen2Elem]]. The Return type will be narrowed in sub
   * traits. */
  def slateX(xOperand: Length): GeomLen2Elem

  /** Translate 2 [[Length]] dimension geometric transformation in the Y dimension returning a [[GeomLen2Elem]]. The Return type will be narrowed in sub
   * traits. */
  def slateY(yOperand: Length): GeomLen2Elem

  /** Uniform 2 [[Length]] dimension geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and
   * preserves [[Circle]]s and [[Square]]s. The Return type will be narrowed in sub traits / classes. */
  def scale(operand: Double): GeomLen2Elem

  /** Divides by a [[Length]] to produce an equivalent [[Aff2Elem]] without [[Length]] units. */
  def mapGeom2(operand: Length): Simil2Elem
}

/** A class that can preserve its type through all the [[Prolign]], proportionate XY axes aligned transformations, using a [[Pt2]] => [[Pt2]] function. These
 * are translate [[SlateXY]], [[Scale]] and negX and negY, the [[MirrorAxes]], transformations. */
trait ProlignLen2Preserve extends Any, GeomLen2Elem
{ /** The most narrow type preserved in some 2d geometric transformations. */
  type ThisT <: ProlignLen2Preserve

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
trait GraphicLen2Elem extends GeomLen2Elem
{ override def slate(operand: VecPtLen2): GraphicLen2Elem
  override def slate(xOperand: Length, yOperand: Length): GraphicLen2Elem
  override def slateX(xOperand: Length): GraphicLen2Elem
  override def slateY(yOperand: Length): GraphicLen2Elem
  override def scale(operand: Double): GraphicLen2Elem
  override def mapGeom2(operand: Length): Graphic2Elem
}

object GraphicLen2Elem
{ /** Implicit [[SlateLen2]] type class instance / evidence for [[GraphicLen2Elem]]. */
  given slateLenEv: SlateLen2[GraphicLen2Elem] = new SlateLen2[GraphicLen2Elem]
  { override def slateT(obj: GraphicLen2Elem, delta: VecPtLen2): GraphicLen2Elem = obj.slate(delta)
    override def slateXY(obj: GraphicLen2Elem, xDelta: Length, yDelta: Length): GraphicLen2Elem = obj.slate(xDelta, yDelta)
    override def slateX(obj: GraphicLen2Elem, xDelta: Length): GraphicLen2Elem = obj.slateX(xDelta)
    override def slateY(obj: GraphicLen2Elem, yDelta: Length): GraphicLen2Elem = obj.slateY(yDelta)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[GraphicLen2Elem]]. */
  given scaleEv: Scale[GraphicLen2Elem] = (obj, operand) => obj.scale(operand)

  /** Implicit [[MapGeom2]] type class instance / evidence for [[GraphicLen2Elem]] and [[Graphic2Elem]]. */
  given mapGeomEv: MapGeom2[GraphicLen2Elem, Graphic2Elem] = (obj, operand) => obj.mapGeom2(operand)
}

/** Type class to translate from [[GeomLen2Elem]]s to [[Geom2]]s. */
trait MapGeom2[A, B]
{ /** Maps from [[GeomLen2Elem]]s to [[Geom2]]s */
  def mapGeom2T(obj: A, operand: Length): B
}

/** Companion object for the Slate type class. Contains implicit instances for collections and other container classes. */
object MapGeom2
{ /** Implicit [[MapGeom2]] type class instance / evidence for [[RArr]]. */
  given rArrEv[A, B](using ev: MapGeom2[A, B], ct: ClassTag[B]): MapGeom2[RArr[A], RArr[B]] = (obj, len) => obj.map(ev.mapGeom2T(_, len))

  /** Implicit [[MapGeom2]] type class instance / evidence for [[Functor]]. This provides instances for List, Option etc. */
  given functorEv[A, B, F[_]](using evF: Functor[F], evA: MapGeom2[A, B]): MapGeom2[F[A], F[B]] =
    (obj, len) => evF.mapT(obj, evA.mapGeom2T(_, len))

  /** Implicit [[MapGeom2]] type class instance / evidence for [[Array]]. */
  given arrayEv[A, B](using ctB: ClassTag[B], evAL: MapGeom2[A, B]): MapGeom2[Array[A], Array[B]] = (obj, len) => obj.map(evAL.mapGeom2T(_, len))
}

extension [A, B](thisA: A)(using ev: MapGeom2[A, B])
{ /** maps the object from [[GeomLen2Elem]]s to the equivalent [[Aff2Elem]]s. */
  def mapGeom2(operand: Length) = ev.mapGeom2T(thisA, operand)
}