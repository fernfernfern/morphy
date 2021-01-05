(ns morphy.templating.site-model-test
  (:require [morphy.templating.site-model :as sut]
            [clojure.test :refer [deftest testing is]]
            [morphy.test-utils :as u]
            [morphy.core :as core]))

(defn get-site-model
  ([dir group-sort-priority]
   (let [input-dir (str u/resources dir)]
     (-> {:input-dir input-dir
          :root-url "https://test.com"
          :groups/sort-priority group-sort-priority}
         (merge (core/build-pages input-dir))
         sut/build))))

(deftest grouping
  (testing "group sort priority can be set manually"
    (let [site (get-site-model "templates/groups/custom-sorted" ["c" "b" "a"])]
      (is (= ["c" "b" "a"]
             (->> site :site/model :site/groups (map :group/label))))))

  (testing "manual group ordering can specify just one item"
    (let [site (get-site-model "templates/groups/custom-sorted" ["b"])]
      (is (= ["b" "a" "c"]
             (->> site :site/model :site/groups (map :group/label)))))))

(deftest templating-groups
  (let [site (u/get-site "templates/groups/with-ungrouped")]
    (testing "it adds posts to groups only if they specify a site/group"
      (is (= "Group A - 2
-----
group A one
group A two

-----
Group B - 3
-----
group B one
group B two
group B three

-----
"
             (u/get-content site ""))))))

(deftest tags
  (let [site (u/get-site "templates/tags/basic")]
    (testing "it groups posts by tag"
      (is (= "one - 2
-----
In one only
In one and two

-----
two - 2
-----
In two only
In one and two

-----
"
             (u/get-content site ""))))))
