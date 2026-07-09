/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb


/** classes are used on the JVM to create user input and select elements in HTML pages. But are used in JavaScript to update the parts of the DOM registered
 * with that updater. */
trait InputLikePost extends InputLike
{
  def nameAttStr: String
  def nameAtt = NameAtt(nameAttStr)

}

trait InputPost extends InputHtml, InputLikePost
{ override def attribs: RArr[XAtt] = RArr(IdAtt(idStr), NameAtt(idStr), typeAtt, valueAtt) ++ otherAttribs
}

/** HTML Input of type text for post requests. */
trait InputStrPost extends InputPost
{ override def typeAtt: TypeTextAtt.type = TypeTextAtt  
}

object InputStrPost
{ /** Factory apply method to create HTML text input. There is an apply name overload that takes the other attributes as repeat parameters. */
  def apply(nameAttStr: String, idStr: String, valueStr: String, otherAttribs: RArr[XAtt]): InputStrPost = new InputStrPostGen(nameAttStr, idStr, valueStr, otherAttribs)

  /** Factory apply method to create HTML text input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def apply(nameAttStr: String, idStr: String, valueStr: String, otherAttribs: XAtt*): InputStrPost = new InputStrPostGen(nameAttStr, idStr, valueStr, otherAttribs.toRArr)

  class InputStrPostGen(val nameAttStr: String, val idStr: String, val valueStr: String, val otherAttribs: RArr[XAtt]) extends InputStrPost
}

trait LabelInputStrPost extends LabelInputLike
{ override def child2: InputStrPost
}

object LabelInputStrPost
{
  def apply(label: String, nameStr: String, idStr: String, valueStr: String): LabelInputStrPost = LabelInputStrPostGen(idStr, nameStr: String, label, valueStr)

  case class LabelInputStrPostGen(label: String, nameStr: String, idStr: String,  valueStr: String) extends LabelInputStrPost
  { override def child2: InputStrPost = InputStrPost(nameStr, idStr, valueStr)
  }
}

class InputPassword(val nameAttStr: String, val idStr: String, val valueStr: String, val otherAttribs: RArr[XAtt]) extends InputPost
{ override def typeAtt: TypePasswordAtt.type = TypePasswordAtt
}

object InputPassword
{ /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as repeat parameters. */
  def apply(nameAttStr: String, idStr: String, valueStr: String, otherAttribs: RArr[XAtt]): InputPassword = new InputPassword(nameAttStr, idStr, valueStr, otherAttribs)

  /** Factory apply method to create HTML password input. There is an apply name overload that takes the other attributes as an [[RArr]]. */
  def apply(nameAttStr: String, idStr: String, valueStr: String, otherAttribs: XAtt*): InputPassword = new InputPassword(nameAttStr, idStr, valueStr, otherAttribs.toRArr)
}

case class LabelInputPassword(label: String, nameAttStr: String, idStr: String, valueStr: String) extends LabelInputLike
{ override def child2: InputPassword = InputPassword(nameAttStr, idStr, valueStr)
}