package com.jobseek.speedjobs.domain.post;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.jobseek.speedjobs.common.exception.DuplicatedException;
import com.jobseek.speedjobs.common.exception.NotFoundException;
import com.jobseek.speedjobs.domain.BaseTimeEntity;
import com.jobseek.speedjobs.domain.tag.Tag;
import com.jobseek.speedjobs.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "posts")
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@ManyToOne(fetch = LAZY, cascade = {PERSIST, MERGE})
	@JoinColumn(name = "user_id")
	private User user;

	private String title;

	@Embedded
	private PostDetail postDetail;

	private int viewCount;

	private int favoriteCount;

	private int commentCount;

	@OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
	private final List<Comment> comments = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "post_tags",
		joinColumns = @JoinColumn(name = "post_id"),
		inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	private final List<Tag> tags = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "post_favorites",
		joinColumns = @JoinColumn(name = "post_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private final List<User> favorites = new ArrayList<>();

	@Builder
	public Post(Long id, String title, PostDetail postDetail) {
		this.id = id;
		this.title = title;
		this.postDetail = postDetail;
	}

	public static Post from(String title, String content) {
		return Post.builder()
			.title(title)
			.postDetail(PostDetail.from(content))
			.build();
	}

	public void increaseViewCount() {
		this.viewCount += 1;
	}

	public void increaseCommentCount() {
		this.commentCount += 1;
	}

	public void decreaseCommentCount() {
		this.commentCount -= 1;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void update(Post post, List<Tag> tags) {
		removeTags();
		addTags(tags);
		title = post.getTitle();
		postDetail = post.getPostDetail();
	}

	public void addTags(List<Tag> tags) {
		for (Tag tag : tags) {
			this.tags.add(tag);
			tag.getPosts().add(this);
		}
	}

	public void removeTags() {
		tags.forEach(tag -> tag.getPosts().remove(this));
		tags.clear();
	}

	public void addFavorite(User user) {
		if (favoriteOf(user)) {
			throw new DuplicatedException("이미 찜한 게시글입니다.");
		}
		favorites.add(user);
		user.getPostFavorites().add(this);
		favoriteCount += 1;
	}

	public void removeFavorite(User user) {
		if (!favoriteOf(user)) {
			throw new NotFoundException("찜 목록에 존재하지 않는 게시글입니다.");
		}
		favorites.remove(user);
		user.getPostFavorites().remove(this);
		favoriteCount -= 1;
	}

	public boolean favoriteOf(User user) {
		if (user == null) {
			return false;
		}
		return user.getPostFavorites().contains(this);
	}
}
