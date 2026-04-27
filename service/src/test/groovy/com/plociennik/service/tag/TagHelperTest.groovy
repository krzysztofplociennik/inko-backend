package com.plociennik.service.tag

import com.plociennik.model.TagEntity
import com.plociennik.model.repository.tag.TagRepository
import spock.lang.Specification

class TagHelperTest extends Specification {

    def tagRepository = Mock(TagRepository)
    def tagHelper = new TagHelper(tagRepository)

    def "should merge existing tags with new tags"() {
        given:
            Set<String> dtoTags = Set.of("CSS", "HTML", "Frontend")
        and:
            tagRepository.findByValueIn(dtoTags) >> List.of(
                    new TagEntity(null, [], "CSS")
            )
        when:
            def actualTags = tagHelper.mergeExistingTagsWithNewTags(dtoTags)
        then:
            actualTags.size() == 3
            actualTags.contains(new TagEntity(null, [], "CSS"))
            actualTags.contains(new TagEntity(null, [], "HTML"))
            actualTags.contains(new TagEntity(null, [], "Frontend"))
    }
}
