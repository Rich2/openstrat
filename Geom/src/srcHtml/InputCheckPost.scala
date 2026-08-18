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
class UsernameInput(val idPrefix: String, val valueStr: String, val regex: Regex, val maxLen: Int, val otherAttribs: RArr[HAtt]) extends InputStrCheckPost
{ /** Maximum length attribute for number of characters. */
  def maxLenAtt =  MaxLengthAtt(maxLen)
  
  override def nameAttStr: String = "username"
  override def idStr: String = idPrefix + nameAttStr
  override def attribs: RArr[HAtt] = RArr(IdAtt(idStr), NameAtt(nameAttStr), typeAtt, valueAtt, maxLenAtt, patternAtt, RequiredAtt) ++ otherAttribs
  override def check(inp: String): ErrBi[Exception, String] = ife(regex.matches(inp), Succ(inp), FailExc(inp -- "does not match Username requirements."))
}

object UsernameInput
{ /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as repeat parameters. */
  def apply(idPrefix: String, valueStr: String, regex: Regex, maxLen: Int, otherAttribs: RArr[HAtt]): UsernameInput =
    new UsernameInput(idPrefix, valueStr, regex, maxLen, otherAttribs)

  /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def apply(idPrefix: String, valueStr: String, regex: Regex = regexStd(maxLenStd), maxLen: Int = maxLenStd): UsernameInput =
    new UsernameInput(idPrefix, valueStr, regex, maxLen, RArr())

  /** Standard maximum number of characters for the username. */
  def maxLenStd: Int = 15

  /** Standard regex for username for the given maximum length. */
  def regexStd(maxLen: Int): Regex = ("[A-Za-z]{4," + maxLen.str + "}[0-9]{0,4}$").r

  /** Standard regex for username. */
  def regexStd: Regex = regexStd(maxLenStd)

  /** Standard regex [[String]] for the username for the given maximum length. */
  def regexStrStd(maxLen: Int): String = regexStd(maxLen).toString

  /** Standard regex [[String]] for the username. */
  def regexStrStd: String = regexStd(maxLenStd).toString
}

/** HTML Input of type password for post requests. */
class PasswordInput(val idPrefix: String, val valueStr: String, val regex: Regex, val otherAttribs: RArr[HAtt]) extends InputCheckPost
{ /** The maximum number of characters for the password. */
  def maxLen: Int = 128
  override def typeAtt: TypePasswordAtt.type = TypePasswordAtt
  override def nameAttStr: String = "password"
  override def idStr: String = idPrefix + nameAttStr
  override def attribs: RArr[HAtt] = RArr(IdAtt(idStr), NameAtt(nameAttStr), typeAtt, valueAtt, patternAtt, MaxLengthAtt(maxLen), RequiredAtt) ++ otherAttribs
  override def check(inp: String): ErrBi[Exception, String] = ife(regex.matches(inp), Succ(inp), FailExc(inp -- "does not match password requirements."))
}

object PasswordInput
{ /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as repeat parameters. */
  def apply(idPrefix: String, valueStr: String, regex: Regex, otherAttribs: RArr[HAtt]): PasswordInput =
    new PasswordInput(idPrefix, valueStr, regex, otherAttribs)

  /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def apply(idPrefix: String, valueStr: String, regex: Regex, otherAttribs: HAtt*): PasswordInput =
    new PasswordInput(idPrefix, valueStr, regex, otherAttribs.toRArr)

  /** Standard maximum number of characters for the password. */
  def maxLenStd: Int = 128
  
  /** Standard regex for the password. */  
  def regexStd: Regex = ("""\S{4,""" + maxLenStd.str + "}$").r
  
  /** Standard regex [[String]] for the password */
  def regexStrStd: String = regexStd.toString
}