/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** A Similar Transformations type class for 2D geometric objects.  */
trait Sim2Trans[T] extends AlignTrans[T]
{ def mirrorT(obj: T, line: LineLike): T
  def rotateT(obj: T, angle: AngleVec): T
}

/** Companion object for the [[Sim2Trans]] geometric transformation set type class trait. */
object Sim2Trans
{
  def apply[A](slateEv: Slate2[A], scaleEv: Scale[A], rotateEv: Rotate[A], mirrorEv: Mirror[A]): Sim2Trans[A] = new Sim2Trans[A]
  { override def slate(obj: A, operand: VecPt2): A = slateEv.slateT(obj, operand)
    override def scale(obj: A, operand: Double): A = scaleEv.scaleT(obj, operand)
    override def rotateT(obj: A, angle: AngleVec): A = rotateEv.rotateT(obj, angle)
    override def mirrorT(obj: A, line: LineLike): A = mirrorEv.mirrorT(obj, line)
  }

  /** Implicit Similar 2-dimensional transformations type class instances / evidence for [[Arr]]. */
  given arrEv[A, ArrA <: Arr[A]](using build: BuilderArrMap[A, ArrA], ev: Sim2Trans[A]): Sim2Trans[ArrA] = new Sim2Trans[ArrA]
  { override def slate(obj: ArrA, operand: VecPt2): ArrA = obj.map(ev.slate(_, operand))
    override def scale(obj: ArrA, operand: Double): ArrA = obj.map(ev.scale(_, operand))
    override def rotateT(obj: ArrA, angle: AngleVec): ArrA = obj.map(ev.rotateT(_, angle))
    override def mirrorT(obj: ArrA, line: LineLike): ArrA = obj.map(ev.mirrorT(_, line))
  }

  /** Implicit Similar 2-dimensional transformations type class instances / evidence provided via [[Functor]] for [[List]], [[Vector]], [[Option]], [[Some]],
   * [[Either]], [[ErrBi]], */
  given functorEv[A, F[_]](using evF: Functor[F], evA: Sim2Trans[A]): Sim2Trans[F[A]] = new Sim2Trans[F[A]]
  { override def slate(obj: F[A], operand: VecPt2): F[A] = evF.mapT(obj, ts => evA.slate(ts, operand))
    override def rotateT(obj: F[A], angle: AngleVec): F[A] = evF.mapT(obj, ts => evA.rotateT(ts, angle))
    override def mirrorT(obj: F[A], line: LineLike): F[A] = evF.mapT(obj, evA.mirrorT(_, line))
    override def scale(obj: F[A], operand: Double): F[A] = evF.mapT[A, A](obj, ts => evA.scale(ts, operand))
  }

  /** Implicit Similar 2-dimensional transformations type class instances / evidence for [[Array]]. */
  given arrayEv[A](using ct: ClassTag[A], ev: Sim2Trans[A]): Sim2Trans[Array[A]] = new Sim2Trans[Array[A]]
  { override def slate(obj: Array[A], operand: VecPt2): Array[A] = obj.map(ev.slate(_, operand))
    override def rotateT(obj: Array[A], angle: AngleVec): Array[A] = obj.map(ev.rotateT(_, angle))
    override def mirrorT(obj: Array[A], line: LineLike): Array[A] = obj.map(ev.mirrorT(_, line))
    override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
  }
}

/** Extension methods for the [[Sim2Trans]] type class. */
extension[T, T1 >: T](value: T)(using ev: Sim2Trans[T1])
{  /** Extension method rotates this object about the given point. A positive rotation is anticlockwise. */
  def rotateAbout(focus: Pt2, rotation: AngleVec): T1 =
  { val r1 = ev.slateFrom(value, focus)
    val r2 = ev.rotateT(r1, rotation)
    ev.slate(r2, focus)
  }

  /** Extension method rotates this object 45 degrees positively or anticlockwise about the given point. */
  def rotate45About(focus: Pt2): T1 = rotateAbout(focus, 45.degsVec)

  /** Extension method rotates this object 45 degrees negatively or clockwise about the given point. */
  def clk45About(focus: Pt2): T1 = rotateAbout(focus, -45.degsVec)
}