/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, collection.mutable.ArrayBuffer

/** Failure to retrieve a unique Setting of the correct type. */
sealed trait SettingFailException extends ParseException
{ /** Name or names of setting(s). */
  def settingStr: String
}

object SettingFailException
{ /** Factory apply method to create a SettingFail [[Exception]]. */
//  def apply(message: String): SettingFailException = new Exception(message) with SettingFailException

  /** Factory apply method to create a SettingFail [[Exception]]. */
//  def apply(tp: TextPosn, detail: String): SettingFailException = new Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail) with SettingFailException

  /** [[EqT]] type class instance / evidence for [[SettingFailException]]. */
  given eqTEv: EqT[SettingFailException] = (pexc1, pexc2) => pexc1.getMessage == pexc2.getMessage

  /** [[ErrMultiBuilder]] type class instance / evidence for [[SettingFailException]]. */
  given multiBuild[E <: SettingFailException]: ErrMultiBuilder[SettingFailException, E, SettingFailMultiExc[E]] = new ErrMultiBuilder[SettingFailException, E, SettingFailMultiExc[E]]
  { override def multi(arr: RArr[E]): SettingFailMultiExc[E] = SettingFailMultiExc(arr)
    override def multi(errs: E*)(using ct: ClassTag[E]): SettingFailMultiExc[E] = SettingFailMultiExc(errs.toRArr)
  }
}

/** Multiple [[SettingFailException]]s. */
class SettingFailMultiExc[E <: SettingFailException](val mems: RArr[E]) extends Exception("${mems.length} settings:" -- mems.mkStr(", ") -- "failed."),
  ParseExcMulti[E], SettingFailException
{ override def settingStr: String = mems.mkStr(_.settingStr, ", ")
}

/** Duplicate settings found. */
class SettingDuplicatesExc(num: Int, val settingStr: String, typeStr: String) extends Exception("${mems.length} $SettingStr settings of type $typeStr found"),
  SettingFailException

/** No Settings [[Exception]]. */
class NoSettingsException(val settingStr: String) extends Exception(s"$settingStr not found, no settings"), SettingFailException

/** Setting nor found [[Exception]]. */
class SettingNotFoundException(val settingStr: String) extends Exception(settingStr -- "setting not found"), SettingFailException

object SettingNotFoundException
{
  def apply(detail: String): SettingNotFoundException = new SettingNotFoundException(detail)
}

/** Setting does not have the correct type. */
class SettingWrongTypeExc(val settingStr: String, typeStr: String) extends Exception(s"$settingStr Setting is not of $typeStr type"), SettingFailException