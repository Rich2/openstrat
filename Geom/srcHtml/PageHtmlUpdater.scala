/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** An HTML page with an accumulator of [[PageHtmlUpdater]]s. */
trait PageHtmlUpdater extends HtmlPageFile
{ given thisPage: PageHtmlUpdater = this
  var inpAcc: RArr[UpdaterInputLike] = RArr()

  def updaterExplain: String = """There are default values here that you can change as you work down the page. Although once you've used a value, stick with it
  |or you will create an inconsistent system. Insert your own values below. the data is used for page generation locally and is not sent back to our
  |servers.""".stripMargin
}

/** An HTML page with an accumulator of [[PageHtmlUpdater]]s, including an [[UpdaterOption]] for operating System. */
trait PageUpdaterOS extends PageHtmlUpdater
{ val opNameLTI: LabelSelectUpdaterAny = LabelSelectUpdaterAny("opName", "Operating System", UbuntuDeriv, ArchDeriv, OtherOperatingSystem)
  val opNameIUT: UpdaterOption = opNameLTI.child2

  val jVer1: Int = 25
  val javaVerLNI: LabelNumInput = LabelNumInput("javaVer", "Java Version", jVer1)
  val javaVerIUN: UpdaterNumInput = javaVerLNI.child2
}