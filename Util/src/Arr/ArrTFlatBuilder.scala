/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, scala.annotation.unused

/** A type class for the building of efficient compact Immutable Arrays through a flatMap method. Instances for this type class for classes / traits
 *  you control should go in the companion object of BB. This is different from the related [[ArrTBuilder]][BB] type class where the instance
 *  should go into the B companion object. */
trait ArrTFlatBuilder[ArrT <: ArrImut[_]] extends ArrTBuilderCommon[ArrT]

/** Companion object for ArrTFlatBuilder, contains implicit instances for atomic value classes. */
object ArrTFlatBuilder extends ArrFlatBuildLowPriority
{ implicit val intsImplicit: ArrTFlatBuilder[Ints] = IntsBuild
  implicit val dblsImplicit: ArrTFlatBuilder[Dbls] = DblsBuild
  implicit val longsImplicit: ArrTFlatBuilder[Longs] = LongsBuild
  implicit val floatImplicit: ArrTFlatBuilder[Floats] = FloatsBuild
  implicit val booleansImplicit: ArrTFlatBuilder[Booleans] = BooleansBuild
}

/** if you create your own specialist Arr class for a type T, make sure that type T extends SpecialT. */
trait SpecialArrT extends Any

trait ArrFlatBuildLowPriority
{ /** This is the fall back builder implicit for Arrs that do not have their own specialist ArrBuildBase classes. It is placed in this low priority trait
   * to gove those specialist Arr classes implicit priority. The notA implicit parameter is to exclude user defined types that have their own
   * specialist Arr classes. */
 implicit def anyImplicit[A](implicit ct: ClassTag[A], @unused notA: Not[ValueNElem]#L[A]): ArrTFlatBuilder[Arr[A]] = new AnyBuild[A]
}