/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
//import scala.reflect.ClassTag

/** Type class for the rotation of objects of type T */
trait Rotate[T]
{ def rotateRadiansT(obj: T, radians: Double): T
}

object Rotate
{
  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: Rotate[A]): Rotate[AA] =
    (obj, radians) => obj.map(ev.rotateRadiansT(_, radians))

}
