/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import util.matching.Regex

trait InputCheckPost extends InputPost
{
  def regex: Regex

  def regexStr = regex.toString()

  def patternAtt: PatternAtt = PatternAtt(regex)
  def check(inp: String): ErrBi[Exception, String] = ife(regex.matches(inp), Succ(inp), FailExc(inp -- "does not match requirements."))

  override def attribs: RArr[HAtt] = RArr(IdAtt(idStr), NameAtt(nameAttStr), typeAtt, valueAtt, patternAtt) ++ otherAttribs
}

trait InputStrCheckPost extends InputStrPost, InputCheckPost

/** HTML Input of type password for post requests. */
class InputUsername(val idPrefix: String, val valueStr: String, val regex: Regex, val otherAttribs: RArr[HAtt]) extends InputStrCheckPost
{ override def nameAttStr: String = "username"
  override def idStr: String = idPrefix + nameAttStr
  override def attribs: RArr[HAtt] = RArr(IdAtt(idStr), NameAtt(nameAttStr), typeAtt, valueAtt, patternAtt, RequiredAtt) ++ otherAttribs
  override def check(inp: String): ErrBi[Exception, String] = ife(regex.matches(inp), Succ(inp), FailExc(inp -- "does not match Username requirements."))
}

object InputUsername
{ /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as repeat parameters. */
  def apply(idPrefix: String, valueStr: String, regex: Regex, otherAttribs: RArr[HAtt]): InputUsername =
    new InputUsername(idPrefix, valueStr, regex, otherAttribs)

  /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def apply(idPrefix: String, valueStr: String, regex: Regex, otherAttribs: HAtt*): InputUsername =
    new InputUsername(idPrefix, valueStr, regex, otherAttribs.toRArr)
}

/** HTML Input of type password for post requests. */
class InputPassword(val idPrefix: String, val valueStr: String, val regex: Regex, val otherAttribs: RArr[HAtt]) extends InputCheckPost
{ override def typeAtt: TypePasswordAtt.type = TypePasswordAtt
  override def nameAttStr: String = "password"
  override def idStr: String = idPrefix + nameAttStr
  override def attribs: RArr[HAtt] = RArr(IdAtt(idStr), NameAtt(nameAttStr), typeAtt, valueAtt, patternAtt, RequiredAtt) ++ otherAttribs
  override def check(inp: String): ErrBi[Exception, String] = ife(regex.matches(inp), Succ(inp), FailExc(inp -- "does not match password requirements."))
}

object InputPassword
{ /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as repeat parameters. */
  def apply(idPrefix: String, valueStr: String, regex: Regex, otherAttribs: RArr[HAtt]): InputPassword =
    new InputPassword(idPrefix, valueStr, regex, otherAttribs)

  /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def apply(idPrefix: String, valueStr: String, regex: Regex, otherAttribs: HAtt*): InputPassword =
    new InputPassword(idPrefix, valueStr, regex, otherAttribs.toRArr)
}