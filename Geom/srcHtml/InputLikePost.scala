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
  def apply(idStr: String, nameAttStr: String, valueStr: String, otherAttribs: RArr[XAtt]): InputStrPost =
    new InputStrPostGen(nameAttStr, idStr, valueStr, otherAttribs)

  /** Factory apply method to create HTML text input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def apply(idStr: String, nameAttStr: String, valueStr: String, otherAttribs: XAtt*): InputStrPost =
    new InputStrPostGen(nameAttStr, idStr, valueStr, otherAttribs.toRArr)

  class InputStrPostGen(val idStr: String, val nameAttStr: String, val valueStr: String, val otherAttribs: RArr[XAtt]) extends InputStrPost
}

/** A Label and an HTML Input of type text for post requests. */
trait LabelInputStrPost extends LabelInputLike
{ override def child2: InputStrPost
}

object LabelInputStrPost
{ /** Factory apply methos to construct a Label and an HTML Input of type text for post requests. */
  def apply(label: String, idStr: String, nameStr: String, valueStr: String): LabelInputStrPost = LabelInputStrPostGen(idStr, nameStr: String, label, valueStr)

  case class LabelInputStrPostGen(labelStr: String, forIdStr: String, nameStr: String, valueStr: String) extends LabelInputStrPost
  { override def child2: InputStrPost = InputStrPost(forIdStr, nameStr, valueStr)
  }
}

/** HTML Input of type password for post requests. */
class InputPassword(val idStr: String, val nameAttStr: String, val valueStr: String, val otherAttribs: RArr[XAtt]) extends InputPost
{ override def typeAtt: TypePasswordAtt.type = TypePasswordAtt
}

object InputPassword
{ /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as repeat parameters. */
  def apply(idStr: String, nameAttStr: String, valueStr: String, otherAttribs: RArr[XAtt]): InputPassword =
    new InputPassword(idStr, nameAttStr, valueStr, otherAttribs)

  /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def apply(idStr: String, nameAttStr: String, valueStr: String, otherAttribs: XAtt*): InputPassword =
    new InputPassword(nameAttStr, idStr, valueStr, otherAttribs.toRArr)
}

/** HTML Labek and Input of type password for post requests. */
case class LabelInputPassword(labelStr: String, forIdStr: String, nameAttStr: String, valueStr: String) extends LabelInputLike
{ override def child2: InputPassword = InputPassword(forIdStr, nameAttStr, valueStr)
}