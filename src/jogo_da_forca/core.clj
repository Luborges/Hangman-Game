(ns jogo-da-forca.core
  (:gen-class))

(def life-total 6)
(def word-list ["sandman", "morpheus", "destiny", "death", "destruction", "despair", "desire", "delirium"])
(def secret-word (clojure.string/upper-case (get word-list (rand-int 7))))

(defn read-letter! [] (clojure.string/upper-case (read-line)))

(defn new-game-option []
	(println "New game?")
	(println "1-New game | 0-Exit")
	(let [option (read-letter!)])
)

(defn lost [] 
	(println "You lost") 
)

(defn win [] 
	(println "You win") 
)

(defn remaining-letters [word hits]
	(remove (fn [letter] (contains? hits (str letter))) word ))

(defn guess-whole-word? [word hits]
	(empty? (remaining-letters word hits))
)

(defn guess? [try word]
	(.contains word try)
)

(defn print-game [lifes word hits]
	(println "Lifes: " lifes)
	(doseq [letter (seq word)]
		(if (contains? hits (str letter))
			(print letter " ")
			(print "_ ")
		)
	)
	(println))

(defn game [lifes word hits]
	(print-game lifes word hits)
	(cond 
		(= lifes 0) (lost)
		(guess-whole-word? word hits) (win)
		:else
		(let [try (read-letter!)]
			(if (guess? try word)
				(do
					(println "You guess the letter!")
					(recur lifes word (conj hits try)))
				(do
					(println "Wrong letter!")
					(recur (dec lifes) word hits))))))

(defn start-game [] (game life-total secret-word #{}))

(defn -main [& args]
  (start-game))