package com.example.mini.domain.like.entity;

    import com.example.mini.domain.member.entity.Member;
    import com.example.mini.domain.accommodation.entity.Accommodation;
    import com.example.mini.global.model.entity.BaseEntity;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.EqualsAndHashCode;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "likes")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Like extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accomodation_id", nullable = false)
  private Accommodation accommodation;

  @Column(name = "is_liked", nullable = false)
  @Setter
  private boolean isLiked;
}
