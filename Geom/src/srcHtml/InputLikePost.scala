/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** classes are used on the JVM to create user input and select elements in HTML pages. But are used in JavaScript to update the parts of the DOM registered
 * with that updater. */
trait InputLikePost extends InputLike
{ /** The [[String]] of the name attribute of this post request input. */
  def nameAttStr: String
  
  /** The name attribute of this post request input. */
  def nameAtt = NameAtt(nameAttStr)
}

trait InputPost extends InputHtml, InputLikePost
{ override def attribs: RArr[HAtt] = RArr(IdAtt(idStr), NameAtt(nameAttStr), typeAtt, valueAtt) ++ otherAttribs
}

/** HTML Input of type text for post requests. */
trait InputStrPost extends InputPost
{ override def typeAtt: TypeTextAtt.type = TypeTextAtt  
}

object InputStrPost
{ /** Factory apply method to create HTML text input. There is an apply name overload that takes the other attributes as repeat parameters. */
  def apply(idStr: String, nameAttStr: String, valueStr: String, otherAttribs: RArr[HAtt]): InputStrPost =
    new InputStrPostGen(nameAttStr, idStr, valueStr, otherAttribs)

  /** Factory apply method to create HTML text input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def apply(idStr: String, nameAttStr: String, valueStr: String, otherAttribs: HAtt*): InputStrPost =
    new InputStrPostGen(idStr, nameAttStr, valueStr, otherAttribs.toRArr)

  /** Factory methods to construct a required HTML Input of type text for post requests. */
  def required(idStr: String, nameAttStr: String, valueStr: String, otherInputAtts: HAtt*): InputStrPost =
      InputStrPostGen(idStr, nameAttStr: String, valueStr, RequiredAtt %: otherInputAtts.toRArr)  

  class InputStrPostGen(val idStr: String, val nameAttStr: String, val valueStr: String, val otherAttribs: RArr[HAtt]) extends InputStrPost
}

/** HTML Input of type password for post requests. */
class InputPassword(val idStr: String, val nameAttStr: String, val valueStr: String, val otherAttribs: RArr[HAtt]) extends InputPost
{ override def typeAtt: TypePasswordAtt.type = TypePasswordAtt
}

object InputPassword
{ /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as repeat parameters. */
  def apply(idStr: String, nameAttStr: String, valueStr: String, otherAttribs: RArr[HAtt]): InputPassword =
    new InputPassword(idStr, nameAttStr, valueStr, otherAttribs)

  /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def apply(idStr: String, nameAttStr: String, valueStr: String, otherAttribs: HAtt*): InputPassword =
    new InputPassword(nameAttStr, idStr, valueStr, otherAttribs.toRArr)

  /** Factory method to create a required HTML password input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def required(idStr: String, nameAttStr: String, valueStr: String, otherAttribs: HAtt*): InputPassword =
      new InputPassword(nameAttStr, idStr, valueStr, RequiredAtt %: otherAttribs.toRArr)  
}