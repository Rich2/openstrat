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

trait RegisterForm extends FormHtml
{
  def userNameStr: String = "userName"
  def passwordStr: String = "password"
  def header = DivHtml("Register".bHtml)
  def userName = LabelInputStrPost.required("User Name", userNameStr, userNameStr, "")
  def password = LabelInputPassword.required("Password", passwordStr, passwordStr, "")
  def submit = SubmitButton("regSubmit")
}

object RegisterForm
{
  def apply(otherAttribs: RArr[XAtt]): RegisterForm = RegisterFormGen(otherAttribs)

  def apply(otherAttribs: XAtt*): RegisterForm = RegisterFormGen(otherAttribs.toRArr)
  
  case class RegisterFormGen(otherAttribs: RArr[XAtt]) extends RegisterForm
  { override def contents: RArr[XCon] = RArr(header, userName, password, submit)
  }
}