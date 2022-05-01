/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

import scala.reflect.ClassTag

trait TransM3[T]
{
  def trans3T(obj: T, f: PtM3 => PtM3): T
}

/** Companion object for the [[TransM3]][T] type class, contains implicit instances for collections and other container classes. */
object TransM3
{
  implicit def arrImplicit[A, AA <: SeqImut[A]](implicit build: ArrBuilder[A, AA], ev: TransM3[A]): TransM3[AA] =
    (obj, f) => obj.map(ev.trans3T(_, f))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransM3[A]): TransM3[F[A]] =
    (obj, f) => evF.mapT(obj, evA.trans3T(_, f))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransM3[A]): TransM3[Array[A]] = (obj, f) => obj.map(ev.trans3T(_, f))
}

class TransM3Extensions[T](value: T, ev: TransM3[T])
{

}