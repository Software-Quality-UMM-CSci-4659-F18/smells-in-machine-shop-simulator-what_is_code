Machine shop simulator
======================

Some quite atrocious code from a quite dated data structures book that makes for an excellent refactoring exercise.

The initial code here _is not mine_. It is one of the example applications taken from the huge pile of example code provided on-line as part of a [10 year old Data Structures book](http://www.cise.ufl.edu/~sahni/dsaaj/). Since the code's not mine, I certainly can't just be slapping a license on it. Given that there hasn't been an update to the associated book in a decade, however, I'm hoping that no one is feeling terribly possessive of this code. I know _I_ don't, and I'm quite happy to have you copy/fork this if you find it interesting or useful. I obviously can't speak for the original author(s), however, so use at your own risk.

I've added a set of acceptance tests (including a number of randomly generated tests) that provide effectively 100% code coverage on the simulator itself. While I seriously doubt that this coverage is in any way "complete", it should provide a reasonable safety net as we refactor the code. The coverage on things like the LinkedQueue are considerably lower, however, so if you wish to refactor those, then you'll need to provide some additional tests (unit tests should do fine) to give you that safety net.
