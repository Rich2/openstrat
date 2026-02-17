/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

object TestPage1 extends HtmlPageFile
{ override def fileNameStem: String = "TestPage1"
  override def titleStr: String = "Endnotes Test page."

  override def body: HtmlBody = HtmlBody(h1, mainSec)

  val h1 = HtmlH1("This is a test page for end notes.")

  def mainDecs = RArr(StyleAtt(MaxWidthDec(68.em), MarginLRAutoDec))
  def mainSec = HtmlSection(RArr(p1, q1, p2, p3, n1, n2), mainDecs)

  implicit val taker: NoteTaker = NoteTaker()

  val p1: HtmlP = HtmlP.id("para1", "I'm going to link this footnote marker", HtmlSup(HtmlA("#note1", "fn1")), """to the footnote at the bottom of the intro
  |text.
  |<br>Once more unto the breach, dear friends, once more;
  |<br>Or close the wall up with our English dead.
  |<br>In peace there's nothing so becomes a man
  |<br>As modest stillness and humility:
  |<br>But when the blast of war blows in our ears,
  |<br>Then imitate the action of the tiger;
  |<br>Stiffen the sinews, summon up the blood,
  |<br>Disguise fair nature with hard-favour'd rage;
  |<br>Then lend the eye a terrible aspect;
  |<br>Let pry through the portage of the head
  |<br>Like the brass cannon; let the brow o'erwhelm it
  |<br>As fearfully as doth a galled rock
  |<br>O'erhang and jutty his confounded base,
  |<br>Swill'd with the wild and wasteful ocean.
  |<br>Now set the teeth and stretch the nostril wide,
  |<br>Hold hard the breath and bend up every spirit
  |<br>To his full height. On, on, you noblest English.
  |<br>Whose blood is fet from fathers of war-proof!
  |<br>Fathers that, like so many Alexanders,
  |<br>Have in these parts from morn till even fought
  |<br>And sheathed their swords for lack of argument:
  |<br>Dishonour not your mothers; now attest
  |<br>That those whom you call'd fathers did beget you.
  |<br>Be copy now to men of grosser blood,
  |<br>And teach them how to war. And you, good yeoman,
  |<br>Whose limbs were made in England, show us here
  |<br>The mettle of your pasture; let us swear
  |<br>That you are worth your breeding; which I doubt not;
  |<br>For there is none of you so mean and base,
  |<br>That hath not noble lustre in your eyes.
  |<br>I see you stand like greyhounds in the slips,
  |<br>Straining upon the start. The game's afoot:
  |<br>Follow your spirit, and upon this charge
  |<br>Cry 'God for Harry, England, and Saint George!'""".stripMargin)

  val q1q = """This is going to be, by our standards here, something of a brief overview, roughly the equivalent to the lecture I give to my students when we
  |cover this period (with a bit more detail, because text is more compressed). A full ‘deep dive’ of all of the debates and open questions of this period would
  |no doubt run quite a few posts and more importantly really ought to be written by specialists in the bronze age. This is also a very archaeologically driven
  |topic, which makes it more sensitive than most to new evidence – archaeological site work, but also epigraphic evidence (mostly on clay tablets) – that can
  |change our understanding of events. As we’ll see, our understanding has changed a fair bit.""".stripMargin
  val q1 = BlockQuoteAnchored(q1q, 2, "https://acoup.blog/2026/01/30/collections-the-late-bronze-age-collapse-a-very-brief-introduction",
    "From A Collection of Unmitigated Pedantry")

  val p2: HtmlP = HtmlP.id("para2", "I'm going to link this footnote marker (2) to the footnote at the bottom of the intro text.",
  """<br>Once more unto the breach, dear friends, once more;
  |<br>Or close the wall up with our English dead.
  |<br>In peace there's nothing so becomes a man
  |<br>As modest stillness and humility:
  |<br>But when the blast of war blows in our ears,
  |<br>Then imitate the action of the tiger;
  |<br>Stiffen the sinews, summon up the blood,
  |<br>Disguise fair nature with hard-favour'd rage;
  |<br>Then lend the eye a terrible aspect;
  |<br>Let pry through the portage of the head
  |<br>Like the brass cannon; let the brow o'erwhelm it
  |<br>As fearfully as doth a galled rock
  |<br>O'erhang and jutty his confounded base,
  |<br>Swill'd with the wild and wasteful ocean.
  |<br>Now set the teeth and stretch the nostril wide,
  |<br>Hold hard the breath and bend up every spirit
  |<br>To his full height. On, on, you noblest English.
  |<br>Whose blood is fet from fathers of war-proof!
  |<br>Fathers that, like so many Alexanders,
  |<br>Have in these parts from morn till even fought
  |<br>And sheathed their swords for lack of argument:
  |<br>Dishonour not your mothers; now attest
  |<br>That those whom you call'd fathers did beget you.
  |<br>Be copy now to men of grosser blood,
  |<br>And teach them how to war. And you, good yeoman,
  |<br>Whose limbs were made in England, show us here
  |<br>The mettle of your pasture; let us swear
  |<br>That you are worth your breeding; which I doubt not;
  |<br>For there is none of you so mean and base,
  |<br>That hath not noble lustre in your eyes.
  |<br>I see you stand like greyhounds in the slips,
  |<br>Straining upon the start. The game's afoot:
  |<br>Follow your spirit, and upon this charge
  |<br>Cry 'God for Harry, England, and Saint George!""".stripMargin)

  val p3: HtmlP = HtmlP.id("para3", "I'm going to link this footnote marker (3) to the footnote at the bottom of the intro text.")

  val n1 = HtmlP.id("note1", "1. A bit more context about paragraph 1.")
  val n2 = HtmlP.id("note2", "2. More on point 2.")
}