/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import util.matching.Regex

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

case class UserDetails(name: String, password: String)

/** Base trait for HTML [[RegisterForm]] and [[LoginForm]] elements. */
trait RegLogForm extends FormHtml
{ /** the name attribute for the Username input. */
  def usernameNameAtt: String = "username"

  /** the name attribute for the Password input. */
  def passwordNameAtt: String = "password"

  /** The prefix for the id attributes for the input fields. */
  def idPrefix: String

  /** The maximum number of characters for the username. */
  def uNameMaxLen: Int = 15

  /** Username HTML input. */
  def uNameInput: InputStrPost = InputStrPost.required(idPrefix + usernameNameAtt, usernameNameAtt, "", MaxLengthAtt(uNameMaxLen))
  
  /** Username HTML label and input. */
  def uNameLI: LabelInput = LabelInput("User Name", uNameInput)

  /** Password HTML input. */
  def passwordInput: InputPassword = InputPassword.required(idPrefix + passwordNameAtt, passwordNameAtt, "")
  
  /** Password HTML label and input. */
  def passwordLI: LabelInput = LabelInput("Password", passwordInput)

  /** Submit HTML input. */
  def submit = SubmitButton(idPrefix + "Submit")
}

/** HTML Register Form element. */
trait RegisterForm extends RegLogForm
{ /** The header for this form. */
  def header = DivHtml("Register".bHtml)
  def regex: Regex = "[a-Z]{4,15}[0-9]{0,11}".r
  
  override def idPrefix: String = "reg"
  override def contents: RArr[XCon] = RArr(header, uNameLI, passwordLI, submit)
  override def attribs: RArr[HAtt] = super.attribs +% PatternAtt(regex)
}

object RegisterForm
{ /** Factory apply method to construct HTML register Form element. There is an apply name overload that takes no other contents and other attributes as repeat
   * parameters. */
    def apply(otherAttribs: RArr[XAtt]): RegisterForm = RegisterFormGen(otherAttribs)

  /** Factory apply method to construct HTML register Form element. There is an apply name overload that takes [[RArr]]s of other content and attributes. */
  def apply(otherAttribs: XAtt*): RegisterForm = RegisterFormGen(otherAttribs.toRArr)

  /** Implementation class for the general case of an HTML register Form element. */
  case class RegisterFormGen(otherAttribs: RArr[XAtt]) extends RegisterForm
}

/** HTML Login Form element. */
trait LoginForm extends RegLogForm
{ /** The header for this form. */
  def header = DivHtml("Login".bHtml)

  override def idPrefix: String = "log"
  override def contents: RArr[XCon] = RArr(header, uNameLI, passwordLI, submit)
}

object LoginForm
{ /** Factory apply method to construct HTML Login Form element. There is an apply name overload that takes no other contents and other attributes as repeat
   * parameters. */
  def apply(otherContents: RArr[XCon], otherAttribs: RArr[XAtt]): LoginForm = LoginFormGen(otherContents, otherAttribs)

  /** Factory apply method to construct HTML Login Form element. There is an apply name overload that takes [[RArr]]s of other content and attributes. */
  def apply(otherAttribs: XAtt*): LoginForm = LoginFormGen(RArr(), otherAttribs.toRArr)

  /** Implementation class for the gneral case of an HTML Login Form element. */
  case class LoginFormGen(otherContents: RArr[XCon], otherAttribs: RArr[XAtt]) extends LoginForm
}