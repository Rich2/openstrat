/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML Form element. */
trait FormHtml extends HtmlTagLines
{ def otherAttribs: RArr[XAtt]
  override def tagName: String = "form"  
  override def attribs: RArr[HAtt] = PostAtt %: otherAttribs
}

object FormHtml
{ /** Factory apply method to construct HTML Form element. There ia an apply name overload that takes the contents and other attributes as [[RArr]]s. */
  def apply(contents: XCon*): FormHtml = FormHtmlGen(contents.toRArr, RArr())
  
  /** Factory apply method to construct HTML Form element. There ia an apply name overload that takes the content as repeat parameters with no other
   * attributes.*/
  def apply(contents: RArr[XCon], otherAttribs: RArr[XAtt]): FormHtml = FormHtmlGen(contents, otherAttribs)

  /** Implementation class for gneeral case of HTML Form element. */
  case class FormHtmlGen(contents: RArr[XCon], otherAttribs: RArr[XAtt]) extends FormHtml
}

/** Base trait for [[RegisterForm]] and [[LoginForm]]. */
trait RegLogForm extends FormHtml
{ /** the name attribute for the Username input. */
  def usernameNameAtt: String = "username"

  /** the name attribute for the Password input. */
  def passwordNameAtt: String = "password"

  /** The prefix for the id attributes for the input fields. */
  def idPrefix: String

  def username: LabelInputStrPost = LabelInputStrPost.required("User Name", idPrefix + usernameNameAtt, usernameNameAtt, "")
  def password: LabelInputPassword = LabelInputPassword.required("Password", idPrefix + passwordNameAtt, passwordNameAtt, "")
  def submit = SubmitButton(idPrefix + "Submit")
}

trait RegisterForm extends RegLogForm
{ def header = DivHtml("Register".bHtml)
  override def idPrefix: String = "reg"
}

object RegisterForm
{
  def apply(otherAttribs: RArr[XAtt]): RegisterForm = RegisterFormGen(otherAttribs)

  def apply(otherAttribs: XAtt*): RegisterForm = RegisterFormGen(otherAttribs.toRArr)
  
  case class RegisterFormGen(otherAttribs: RArr[XAtt]) extends RegisterForm
  { override def contents: RArr[XCon] = RArr(header, username, password, submit)
  }
}