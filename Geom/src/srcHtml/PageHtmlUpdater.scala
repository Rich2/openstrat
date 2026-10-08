/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** An HTML page with an accumulator of [[PageHtmlUpdater]]s. */
trait PageHtmlUpdater extends HtmlPageFile
{ given thisPage: PageHtmlUpdater = this
  var inpAcc: RArr[UpdaterInputLike] = RArr()

  val uExp1: String = "There are default values here that you can change as you work down the page."
  val uExp2: String = "Insert your own values below. The data is used for page generation locally and is not sent back to our servers."
  
  def updaterExplain: String = uExp1 -- uExp2
  
  def updaterExplainFixed: String = uExp1 -- "Although once you've used a value, stick with it or you will create an inconsistent system." -- uExp2
}

/** An HTML page with an accumulator of [[PageHtmlUpdater]]s, including an [[UpdaterSelect]] for operating System. */
trait PageUpdaterOperatingSystem extends PageHtmlUpdater
{ /** Initial value for Java version. */
  val jVer1: Int = 26
  
  val javaVerInput: UpdaterIntInput = UpdaterIntInput("javaVer", jVer1, 17, 30)
  val javaVerLI: LabelInput = LabelInput("Java Version", javaVerInput)
  
  /** Initial value for computer name. */
  val computerName1: String = "computer"

  /** Updater for computer name. */
  val computerNameInput: UpdaterInputStr = UpdaterInputStr("cName", computerName1)

  /** [[UpdaterInputStr]] and it's label for computer name. */
  val computerNameLI: LabelInput = LabelInput("Computer Name", computerNameInput)

  val opSysInput: UpdaterSelect = UpdaterSelect("opName", UbuntuDeriv, CachyOS, ArchOther, OtherOperatingSystem)
  val opSysLI: LabelInput = LabelInput("Operating System", opSysInput)
}