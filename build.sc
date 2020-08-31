// build.sc
import mill._, scalalib._, scalajslib._, scalanativelib._, publish._

trait Common extends ScalaModule
{ def version = "0.2.1snap"
  def scalacOptions = Seq("-feature", "-language:higherKinds,implicitConversions", "-deprecation", "-target:jvm-1.8", "-Ywarn-value-discard", "-encoding", "UTF-8", "-unchecked", "-Xlint")
}

trait CommonStd extends Common
{ def scalaVersion = "2.13.3"
}

trait CommonStdJs extends ScalaJSModule with CommonStd
{ def scalaJSVersion = "1.1.1"
}

trait PlatformsModule extends ScalaModule with CommonStd
{ outer =>  
  
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'srcJvm, millSourcePath / 'srcExs, millSourcePath / 'srcFx)

  trait InnerJs extends CommonStdJs
  { def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcJs)
	  def ivyDeps = outer.ivyDeps() ++  Agg(ivy"org.scala-js::scalajs-dom_sjs1.0:1.0.0")
  }

  trait InnerNative extends ScalaNativeModule with CommonStd
  { def scalaVersion = "2.11.12"
    def scalaNativeVersion = "0.3.8"  
	  def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcNat)
	  def ivyDeps = outer.ivyDeps() //++ ivyNat()	 
  }

  trait InnerTests extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.7.5")
    def testFrameworks = Seq("utest.runner.Framework") 
    def sources = T.sources(millSourcePath / 'src)
    def resources = T.sources(millSourcePath / 'res)
  }

  trait InnerLearn extends ScalaModule with CommonStd
  {
  	def sources = T.sources(outer.millSourcePath / 'src, millSourcePath / 'src)
  }
}

object Util extends PlatformsModule
{
  object MacrosJvm extends CommonStd with PublishModule
  { def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
    def sources = T.sources(Util.millSourcePath / 'Macros / 'src)
    def publishVersion = "0.2.1snap"
    def pomSettings = PomSettings(
      description = "openstrat",
      organization = "com.richstrat",
      url = "https://github.com/richtype/openstrat",
      licenses = Seq(License.MIT),
      versionControl = VersionControl.github("richtype", "openstrat"),
      developers = Seq(
        Developer("richtype", "Rich Oliver","https://github.com/richtype")
      )
    )
  }

  object MacrosJs extends CommonStdJs

  def moduleDeps = Seq(MacrosJvm)
  object test extends InnerTests
  
  object js extends InnerJs
  { def moduleDeps = Seq(MacrosJs)
  }

  object examples extends InnerLearn
  {	def moduleDeps = Seq(Util)
  }

  object Nat extends InnerNative  
}

object Graphics extends PlatformsModule
{ def moduleDeps = Seq(Util)  
  object test extends InnerTests
  
  object js extends InnerJs {  def moduleDeps = Seq(Util.js)  }
  object Nat extends InnerNative

  object examples extends InnerLearn
  { def moduleDeps = Seq(Graphics)
  }

  def ivyDeps = Agg(ivy"org.openjfx:javafx:13.0.2")
}

object Tiling extends PlatformsModule
{ def moduleDeps = Seq(Graphics)  
  object test extends InnerTests
  
  object js extends InnerJs {  def moduleDeps = Seq(Graphics.js)  }
  object Nat extends InnerNative

  object examples extends InnerLearn
  { def moduleDeps = Seq(Tiling)
  }
}

object World extends PlatformsModule
{ def moduleDeps = Seq(Tiling)  

  object test extends InnerTests
      
  object js extends InnerJs { def moduleDeps = Seq(Tiling.js) }
  object Nat extends InnerNative
}

object Dev extends PlatformsModule
{ def moduleDeps = Seq(World)
  def mainClass = Some("ostrat.pFx.DevApp")
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'srcJvm , Graphics.millSourcePath / 'learn / 'src, World.millSourcePath / 'learn / 'src)
  def resources = T.sources(millSourcePath / 'User)


  object js extends InnerJs
  { def moduleDeps = Seq(World.js)
    def sources = T.sources(millSourcePath / 'src, Dev.millSourcePath / 'srcLearn)
  } 
}

def run() = Dev.runBackground()
def test = Util.test
def jsfast = Dev.js.fastOpt
def jsfull = Dev.js.fullOpt