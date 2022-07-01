/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, scala.annotation.unused

/** A type class for the building of efficient compact Immutable Arrays through a flatMap method. Instances for this type class for classes / traits
 *  you control should go in the companion object of BB. This is different from the related [[ArrBuilder]][BB] type class where the instance
 *  should go into the B companion object. */
trait ArrFlatBuilder[ArrB <: SeqImut[_]] extends SeqDefBuilderCommon[ArrB]

/** Companion object for ArrTFlatBuilder, contains implicit instances for atomic value classes. */
object ArrFlatBuilder extends ArrFlatBuilderLowPriority
{ implicit val intsImplicit: ArrFlatBuilder[IntArr] = IntsBuild
  implicit val dblsImplicit: ArrFlatBuilder[DblArr] = DblsBuild
  implicit val longsImplicit: ArrFlatBuilder[LongArr] = LongsBuild
  implicit val floatImplicit: ArrFlatBuilder[FloatArr] = FloatsBuild
  implicit val booleansImplicit: ArrFlatBuilder[BooleanArr] = BooleansBuild
}

/** if you create your own specialist Arr class for a type T, make sure that type T extends SpecialT. */
trait SpecialArrT extends Any

trait ArrFlatBuilderLowPriority
{ /** This is the fall back builder implicit for Arrs that do not have their own specialist ArrBuildBase classes. It is placed in this low priority trait
   * to gove those specialist Arr classes implicit priority. The notA implicit parameter is to exclude user defined types that have their own
   * specialist Arr classes. */
 implicit def anyImplicit[A](implicit ct: ClassTag[A], @unused notA: Not[ElemValueN]#L[A]): ArrFlatBuilder[Arr[A]] = new ArrTBuild[A]
}