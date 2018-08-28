package io.annot8.core.data;

import io.annot8.core.exceptions.UnsupportedContentException;
import io.annot8.core.helpers.WithGroups;
import io.annot8.core.helpers.WithId;
import io.annot8.core.helpers.WithMutableProperties;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Base item interface from which all item implementations extend.
 */
public interface Item extends WithId, WithMutableProperties, WithGroups {

  /**
   * Check if content name exists
   *
   * @return true if this item has a content object with the specified name
   */
  default boolean hasContentOfName(final String name) {
    return listNames().anyMatch(name::equals);
  }

  /**
   * The names of the content objects currently held within this item
   *
   * @return content names
   */
  default Stream<String> listNames() {
    return getContents().map(Content::getName);

  }

  /**
   * The content object for the specified id
   *
   * @param id the content id
   * @return the content if it exists
   */
  Optional<Content<?>> getContent(final String id);

  /**
   * The content object for the specified name
   *
   * @param name the content name
   * @return the content if it exists
   */
  default Stream<Content<?>> getContentByName(final String name) {
    return getContents().filter(c -> c.getName().equals(name));
  }

  /**
   * All content objects contained within this item
   *
   * @return all content
   */
  Stream<Content<?>> getContents();

  /**
   * All content objects of the specified class contained within this item
   *
   * @param clazz the content class to filter against
   * @return content
   */
  <T extends Content<?>> Stream<T> getContents(final Class<T> clazz);

  /**
   * Create a new content builder to generate content.
   *
   * @param clazz the top level content type required
   * @return content builder
   * @throws UnsupportedContentException if the clazz can't be created
   */
  <C extends Content<D>, D> Content.Builder<C, D> create(Class<C> clazz)
      throws UnsupportedContentException;

  /**
   * Remove the specified content object from this item
   *
   * @param id the content id
   */
  void removeContent(final String id);

  /**
   * Creates a new item, with the current item set as the parent.
   *
   * @return new item
   */
  Item createChildItem();

  /**
   * Stop processing this item any further.
   *
   * Note that it is up to the underlying implementation as to whether they delete existing output
   * from this item or not.
   */
  void discard();

  /**
   * If this item is to be discarded at the end of current processing
   *
   * @return true if discarded
   */
  boolean isDiscarded();
}
