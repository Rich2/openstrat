/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, scala.annotation.unused

/** ArrFlatBuild[BB] is a type class for the building of efficient compact Immutable Arrays through a flatMap method. Instances for this typeclass for
 *  classes / traits you control should go in the companion object of BB. This is different from the related ArrBuild[BB] typeclass where the instance
 *  should go into the B companion object. */
trait ArrFlatBuild[ArrT <: ArrBase[_]] extends ArrBuildBase[ArrT]

object ArrFlatBuild extends ArrFlatBuildLowPriority
{ implicit val intsImplicit: ArrFlatBuild[Ints] = IntsBuild
  implicit val dblsImplicit = DblsBuild
  implicit val longsImplicit = LongsBuild
  implicit val floatImplicit = FloatsBuild
  implicit val booleansImplicit = BooleansBuild
}

/** if you create your own specialist Arr class for a type T, make sure that type T extends SpecialT. */
trait SpecialArrT extends Any

trait ArrFlatBuildLowPriority
{ /** This is the fall back builder implicit for Arrs that do not have their own specialist ArrBuildBase classes. It is placed in this low priority trait
   * to gove those specialist Arr classes implicit priority. The notA implicit parameter is to exclude user defined types that have their own
   * specialist Arr classes. */
 implicit def anyImplicit[A](implicit ct: ClassTag[A], @unused notA: Not[ProdHomo]#L[A]) = new AnyBuild[A]
}