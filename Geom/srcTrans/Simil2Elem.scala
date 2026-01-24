/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

trait Simil2Elem extends Any//, Axlign2Elem
{
  /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters on this GeomElem returning a GeomElem. The Return type will be
   * narrowed in sub traits. End users will often want to use the slate method taking a [[Pt2]] or [[Vec2]] as a parameter, the slateX or the slateY methods.
   * These methods will be offered as extension methods using this method for their implementations. */
  def slate(operand: VecPt2): Simil2Elem

  /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters on this GeomElem returning a GeomElem. The Return type will be
   * narrowed in sub traits. End users will often want to use the slate method taking a [[Pt2]] or [[Vec2]] as a parameter, the slateX or the slateY methods.
   * These methods will be offered as extension methods using this method for their implementations. */
  def slate(xOperand: Double, yOperand: Double): Simil2Elem

  /** Translate 2D geometric transformation in the X dimension, returning a GeomElem. The Return type will be narrowed in sub traits. */
  def slateX(operand: Double): Simil2Elem

  /** Translate 2D geometric transformation in the Y dimension, returning a GeomElem. The Return type will be narrowed in sub traits. */
  def slateY(operand: Double): Simil2Elem

  def slateFrom(operand: VecPt2): Simil2Elem

  def slateFrom(xOperand: Double, yOperand: Double): Simil2Elem

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  def scale(operand: Double): Simil2Elem

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  def negX: Simil2Elem

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  def negY: Simil2Elem

  /** Rotation positive or anti-clockwise 90 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed in
   * subclasses and traits. */
  def rotate90: Simil2Elem

  /** Rotation of 180 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed in subclasses and traits. */
  def rotate180: Simil2Elem

  /** Rotation positive or anti-clockwise 270 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed in
   * subclasses and traits. */
  def rotate270: Simil2Elem

  /** 2D Transformation using a [[AxlignMatrix]]. The return type will be narrowed in subclasses / traits. */
  def prolign(matrix: AxlignMatrix): Simil2Elem

  /** Rotation 2D geometric transformation on a GeomElem. The return type will be narrowed in subclasses and traits. */
  def rotate(rotation: AngleVec): Simil2Elem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GeomElem. The return type will be narrowed in subclasses and traits. */
  def mirror(lineLike: LineLike): Simil2Elem
}

/** A Similar Transformations type class for 2D geometric objects.  */
trait Similar2Trans[T] extends TransAlign[T]
{ def reflectT(obj: T, line: LineLike): T
  def rotate(obj: T, angle: AngleVec): T
}

/** Companion object for the [[Similar2Trans]] geometric transformation set type class trait. */
object Similar2Trans
{ /** Implicit Similar 2-dimensional transformations type class instances / evidence for [[Arr]]. */
  given arrEv[A, ArrA <: Arr[A]](using build: BuilderArrMap[A, ArrA], ev: Similar2Trans[A]): Similar2Trans[ArrA] = new Similar2Trans[ArrA]
  { override def slate(obj: ArrA, offset: VecPt2): ArrA = obj.map(ev.slate(_, offset))
    override def rotate(obj: ArrA, angle: AngleVec): ArrA = obj.map(ev.rotate(_, angle))
    override def reflectT(obj: ArrA, line: LineLike): ArrA = obj.map(ev.reflectT(_, line))
    override def scale(obj: ArrA, operand: Double): ArrA = obj.map(ev.scale(_, operand))
  }

  /** Implicit Similar 2-dimensional transformations type class instances / evidence provided via [[Functor]] for [[List]], [[Vector]], [[Option]], [[Some]],
   * [[Either]], [[ErrBi]], */
  given functorEv[A, F[_]](using evF: Functor[F], evA: Similar2Trans[A]): Similar2Trans[F[A]] = new Similar2Trans[F[A]]
  { override def slate(obj: F[A], offset: VecPt2): F[A] = evF.mapT(obj, ts => evA.slate(ts, offset))
    override def rotate(obj: F[A], angle: AngleVec): F[A] = evF.mapT(obj, ts => evA.rotate(ts, angle))
    override def reflectT(obj: F[A], line: LineLike): F[A] = evF.mapT(obj, evA.reflectT(_, line))
    override def scale(obj: F[A], operand: Double): F[A] = evF.mapT[A, A](obj, ts => evA.scale(ts, operand))
  }

  /** Implicit Similar 2-dimensional transformations type class instances / evidence for [[Array]]. */
  given arrayEv[A](using ct: ClassTag[A], ev: Similar2Trans[A]): Similar2Trans[Array[A]] = new Similar2Trans[Array[A]]
  { override def slate(obj: Array[A], offset: VecPt2): Array[A] = obj.map(ev.slate(_, offset))
    override def rotate(obj: Array[A], angle: AngleVec): Array[A] = obj.map(ev.rotate(_, angle))
    override def reflectT(obj: Array[A], line: LineLike): Array[A] = obj.map(ev.reflectT(_, line))
    override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
  }
}

/** Extension methods for the [[Similar2Trans]] type class. */
extension[T, T1 >: T](value: T)(using ev: Similar2Trans[T1])
{  /** Extension method rotates this object about the given point. A positive rotation is anticlockwise. */
  def rotateAbout(focus: Pt2, rotation: AngleVec): T1 =
  { val r1 = ev.slateFrom(value, focus)
    val r2 = ev.rotate(r1, rotation)
    ev.slate(r2, focus)
  }

  /** Extension method rotates this object 45 degrees positively or anticlockwise about the given point. */
  def rotate45About(focus: Pt2): T1 = rotateAbout(focus, 45.degsVec)

  /** Extension method rotates this object 45 degrees negatively or clockwise about the given point. */
  def clk45About(focus: Pt2): T1 = rotateAbout(focus, -45.degsVec)
}